package com.pawsup.break_experience

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pawsup.cats.Cat
import com.pawsup.cats.CatAssetResolver
import com.pawsup.cats.CatRegistry
import com.pawsup.break_experience.components.*
import com.pawsup.paywall.PaywallActivity
import com.pawsup.ui.theme.PawsUpTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakOverlayActivity : ComponentActivity() {

    @Inject lateinit var catRegistry: CatRegistry

    private val vm: BreakOverlayViewModel by viewModels()
    private var greetingPlayer: MediaPlayer? = null
    private var purrPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val catId = intent.getStringExtra(EXTRA_CAT_ID) ?: "miso"
        val cat = catRegistry.find(catId)
        vm.onEntranceStarted(cat)
        prepareAudio()

        setContent {
            PawsUpTheme {
                val state by vm.state.collectAsStateWithLifecycle()
                LaunchedEffect(Unit) {
                    vm.finishEvent.collect { summary ->
                        RecapToast.show(this@BreakOverlayActivity, summary)
                        finish()
                    }
                }
                BreakOverlayScreen(
                    state = state,
                    cat = cat,
                    onEntranceComplete = vm::onEntranceComplete,
                    onOutroComplete = vm::onOutroComplete,
                    onPet = { vm.onPetTapped(); playPurr(cat) },
                    onLongPress = vm::onLongPressEscape,
                    onEscapeConfirmed = vm::onEscapeConfirmed,
                    onGuestMeet = { guestCat ->
                        vm.dismissGuestVisit()
                        startActivity(
                            Intent(this, PaywallActivity::class.java)
                                .putExtra(PaywallActivity.EXTRA_CAT_ID, guestCat.id)
                        )
                    },
                    onGuestDismiss = vm::dismissGuestVisit
                )
            }
        }
        playGreeting(cat)
    }

    override fun onDestroy() {
        super.onDestroy()
        greetingPlayer?.release()
        purrPlayer?.release()
    }

    private fun prepareAudio() {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        runCatching {
            greetingPlayer = MediaPlayer().apply {
                setAudioAttributes(attrs)
                assets.openFd("sounds/greeting.mp3").use { fd ->
                    setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
                }
                prepare()
            }
        }
        runCatching {
            purrPlayer = MediaPlayer().apply {
                setAudioAttributes(attrs)
                assets.openFd("sounds/purr.mp3").use { fd ->
                    setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
                }
                prepare()
            }
        }
    }

    private fun playGreeting(cat: Cat) {
        runCatching {
            greetingPlayer?.let {
                if (!it.isPlaying) {
                    it.playbackParams = android.media.PlaybackParams().setPitch(cat.greetingPitchShift)
                    it.start()
                }
            }
        }
    }

    private fun playPurr(cat: Cat) {
        runCatching {
            purrPlayer?.let {
                if (it.isPlaying) it.seekTo(0)
                it.playbackParams = android.media.PlaybackParams().setPitch(cat.greetingPitchShift)
                it.start()
            }
        }
    }

    companion object {
        const val EXTRA_CAT_ID = "cat_id"
        const val EXTRA_BREAK_MINUTES = "break_duration_minutes"
    }
}

@Composable
private fun BreakOverlayScreen(
    state: BreakUiState,
    cat: Cat,
    onEntranceComplete: () -> Unit,
    onOutroComplete: () -> Unit,
    onPet: () -> Unit,
    onLongPress: () -> Unit,
    onEscapeConfirmed: () -> Unit,
    onGuestMeet: (Cat) -> Unit,
    onGuestDismiss: () -> Unit
) {
    // Background is the cat's own background color — the video's solid bg matches,
    // so the cat floats naturally without any chroma key processing.
    val bgColor = remember(cat.backgroundColorHex) {
        Color(android.graphics.Color.parseColor(cat.backgroundColorHex))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        LongPressEscapeLayer(
            showEscapeText = state.showEscapeText,
            onLongPress = onLongPress,
            onEscapeConfirmed = onEscapeConfirmed,
            modifier = Modifier.fillMaxSize()
        ) {
            // Cat video — background color matches overlay so cat floats naturally
            BreakVideoPlayer(
                catId = cat.id,
                breakState = state.breakState,
                onEntranceComplete = onEntranceComplete,
                onOutroComplete = onOutroComplete
            )

            // Invisible pet tap zone: lower 50% width × lower 40% height
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.4f)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { onPet() })
                    }
            )

            // Dialogue bubble — top, below status bar
            CatDialogueBubble(
                line = state.currentDialogueLine,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 20.dp)
            )

            // Timer — below dialogue, just the countdown, 80% opacity
            BreakTimerDisplay(
                remainingSeconds = state.remainingSeconds,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 100.dp)
            )

            // Pet hint fades in at t=10s
            AnimatedVisibility(
                visible = state.showPetHint,
                enter = fadeIn(tween(600)),
                exit = fadeOut(tween(600)),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp)
            ) {
                Text(
                    text = stringResource(com.pawsup.R.string.break_pet_hint),
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 14.sp
                )
            }
        }

        // Guest visit overlay (rendered above long-press layer)
        if (state.breakState is BreakState.GuestVisit) {
            GuestVisitOverlay(
                guestCat = state.breakState.guestCat,
                onMeetGuest = onGuestMeet,
                onDismiss = onGuestDismiss
            )
        }
    }
}
