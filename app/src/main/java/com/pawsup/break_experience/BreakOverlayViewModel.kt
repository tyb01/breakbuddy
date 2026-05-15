package com.pawsup.break_experience

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.os.SystemClock
import com.pawsup.cats.Cat
import com.pawsup.cats.CatRegistry
import com.pawsup.cats.CatVoiceProvider
import com.pawsup.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

data class BreakUiState(
    val cat: Cat? = null,
    val breakState: BreakState = BreakState.Entrance,
    val remainingSeconds: Int = 0,
    val currentDialogueLine: String = "",
    val sessionPetCount: Int = 0,
    val showPetHint: Boolean = false,
    val showEscapeText: Boolean = false,
    val totalBreaks: Int = 0
)

@HiltViewModel
class BreakOverlayViewModel @Inject constructor(
    private val prefs: UserPreferences,
    private val catRegistry: CatRegistry,
    savedState: SavedStateHandle
) : ViewModel() {

    private val catId: String = savedState[BreakOverlayActivity.EXTRA_CAT_ID] ?: "miso"
    private val breakMinutes: Int = savedState[BreakOverlayActivity.EXTRA_BREAK_MINUTES] ?: 2

    private val _state = MutableStateFlow(BreakUiState())
    val state: StateFlow<BreakUiState> = _state.asStateFlow()

    private val _finishEvent = MutableSharedFlow<BreakSummary>(extraBufferCapacity = 1)
    val finishEvent: SharedFlow<BreakSummary> = _finishEvent.asSharedFlow()

    private var timerJob: Job? = null
    private var dialogueJob: Job? = null
    private var guestScheduleJob: Job? = null
    private var entranceDuration = 5_000L
    private var outroDuration = 5_000L
    private var dialogueLines: List<String> = emptyList()
    private var dialogueIndex = 0
    private val companionEntered = AtomicBoolean(false)

    fun onEntranceStarted(cat: Cat) {
        val lines = CatVoiceProvider.getLoopLines(cat.voiceBucket)
        dialogueLines = lines
        _state.update { it.copy(
            cat = cat,
            currentDialogueLine = CatVoiceProvider.getEntrance(cat.voiceBucket),
            remainingSeconds = breakMinutes * 60
        )}
        viewModelScope.launch {
            delay(1_000) // spec: entrance line shown at t=1s
        }
    }

    fun onEntranceComplete() {
        if (!companionEntered.compareAndSet(false, true)) return
        _state.update { it.copy(breakState = BreakState.CompanionLoop) }
        startCompanionLoop()
    }

    fun setEntranceDuration(ms: Long) { entranceDuration = ms }
    fun setOutroDuration(ms: Long) { outroDuration = ms }

    private fun startCompanionLoop() {
        timerJob?.cancel()
        dialogueJob?.cancel()

        timerJob = viewModelScope.launch {
            val totalSeconds = breakMinutes * 60
            var deadlineElapsed = SystemClock.elapsedRealtime() + totalSeconds * 1_000L
            var frozenSecondsDuringGuest: Int? = null

            while (true) {
                val now = SystemClock.elapsedRealtime()
                val bs = _state.value.breakState

                if (bs is BreakState.Outro || bs is BreakState.Done) return@launch

                if (bs !is BreakState.GuestVisit) {
                    frozenSecondsDuringGuest?.let { r ->
                        deadlineElapsed = now + r * 1_000L
                        frozenSecondsDuringGuest = null
                    }
                }
                if (bs is BreakState.GuestVisit && frozenSecondsDuringGuest == null) {
                    frozenSecondsDuringGuest =
                        ((deadlineElapsed - now) / 1_000L).toInt().coerceIn(0, totalSeconds)
                }

                val remaining = frozenSecondsDuringGuest
                    ?: ((deadlineElapsed - now) / 1_000L).toInt().coerceIn(0, totalSeconds)

                _state.update { it.copy(remainingSeconds = remaining) }
                if (remaining <= 0) break

                delay(250L)
            }
            onLoopComplete()
        }

        dialogueJob = viewModelScope.launch {
            delay(500)
            while (true) {
                val line = dialogueLines.getOrElse(dialogueIndex % dialogueLines.size) { "" }
                _state.update { it.copy(currentDialogueLine = line) }
                dialogueIndex++
                delay(25_000)
            }
        }

        // pet hint at t=10s
        viewModelScope.launch {
            delay(10_000)
            _state.update { it.copy(showPetHint = true) }
            delay(5_000)
            _state.update { it.copy(showPetHint = false) }
        }

        scheduleGuestVisit()
    }

    private fun scheduleGuestVisit() {
        guestScheduleJob = viewModelScope.launch {
            val totalBreaks = prefs.snapshotTotalBreaks()
            if (totalBreaks < 3) return@launch
            if (Random.nextFloat() > 0.15f) return@launch

            val ownedIds = prefs.snapshotOwnedCatIds()
            val bundleOwned = prefs.snapshotHasCafeBundle()
            if (bundleOwned) return@launch

            val candidates = catRegistry.premium().filter { cat ->
                !cat.id.let { ownedIds.contains(it) } &&
                    (System.currentTimeMillis() - prefs.getGuestVisitLastShown(cat.id)) > 7 * 86_400_000L
            }
            if (candidates.isEmpty()) return@launch

            val guest = candidates.random()
            val delayMs = (20_000L..40_000L).random()
            delay(delayMs)

            if (_state.value.breakState == BreakState.CompanionLoop) {
                prefs.setGuestVisitLastShown(guest.id, System.currentTimeMillis())
                _state.update { it.copy(breakState = BreakState.GuestVisit(guest)) }
                delay(8_000)
                if (_state.value.breakState is BreakState.GuestVisit) {
                    _state.update { it.copy(breakState = BreakState.CompanionLoop) }
                }
            }
        }
    }

    fun dismissGuestVisit() {
        _state.update { it.copy(breakState = BreakState.CompanionLoop) }
    }

    private fun onLoopComplete() {
        timerJob?.cancel()
        dialogueJob?.cancel()
        guestScheduleJob?.cancel()
        _state.update { it.copy(
            breakState = BreakState.Outro,
            currentDialogueLine = _state.value.cat?.voiceBucket?.let {
                CatVoiceProvider.getGoodbye(it)
            } ?: ""
        )}
    }

    fun onOutroComplete() {
        viewModelScope.launch {
            prefs.incrementBreaksCompleted()
            val cat = _state.value.cat
            val summary = BreakSummary(
                catName = cat?.displayName ?: "Miso",
                durationMinutes = breakMinutes,
                petCount = _state.value.sessionPetCount
            )
            _state.update { it.copy(breakState = BreakState.Done) }
            _finishEvent.emit(summary)
        }
    }

    fun onPetTapped() {
        viewModelScope.launch {
            _state.update { it.copy(sessionPetCount = it.sessionPetCount + 1) }
            prefs.incrementPetsLifetime()
        }
    }

    fun onLongPressEscape() {
        _state.update { it.copy(showEscapeText = true) }
    }

    fun onEscapeConfirmed() {
        timerJob?.cancel()
        dialogueJob?.cancel()
        guestScheduleJob?.cancel()
        _state.update { it.copy(
            breakState = BreakState.Outro,
            currentDialogueLine = _state.value.cat?.voiceBucket?.let {
                CatVoiceProvider.getGoodbye(it)
            } ?: "",
            showEscapeText = false
        )}
    }

    private fun LongRange.random() = Random.nextLong(first, last + 1)
}

data class BreakSummary(
    val catName: String,
    val durationMinutes: Int,
    val petCount: Int
)
