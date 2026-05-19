package com.pawsup.break_experience;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import android.os.SystemClock;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatRegistry;
import com.pawsup.cats.CatVoiceProvider;
import com.pawsup.data.UserPreferences;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicBoolean;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\'\u001a\u00020(J\u0006\u0010)\u001a\u00020(J\u000e\u0010*\u001a\u00020(2\u0006\u0010+\u001a\u00020,J\b\u0010-\u001a\u00020(H\u0002J\u0006\u0010.\u001a\u00020(J\u0006\u0010/\u001a\u00020(J\b\u00100\u001a\u00020(H\u0002J\u000e\u00101\u001a\u00020(2\u0006\u00102\u001a\u00020\u001bJ\u000e\u00103\u001a\u00020(2\u0006\u00102\u001a\u00020\u001bJ\b\u00104\u001a\u00020(H\u0002J\f\u00105\u001a\u00020\u001b*\u000206H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0010\u0010 \u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000e0#\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0010\u0010&\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2 = {"Lcom/pawsup/break_experience/BreakOverlayViewModel;", "Landroidx/lifecycle/ViewModel;", "prefs", "Lcom/pawsup/data/UserPreferences;", "catRegistry", "Lcom/pawsup/cats/CatRegistry;", "savedState", "Landroidx/lifecycle/SavedStateHandle;", "(Lcom/pawsup/data/UserPreferences;Lcom/pawsup/cats/CatRegistry;Landroidx/lifecycle/SavedStateHandle;)V", "_finishEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/pawsup/break_experience/BreakSummary;", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pawsup/break_experience/BreakUiState;", "breakMinutes", "", "catId", "", "companionEntered", "Ljava/util/concurrent/atomic/AtomicBoolean;", "dialogueIndex", "dialogueJob", "Lkotlinx/coroutines/Job;", "dialogueLines", "", "entranceDuration", "", "finishEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getFinishEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "guestScheduleJob", "outroDuration", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "timerJob", "dismissGuestVisit", "", "onEntranceComplete", "onEntranceStarted", "cat", "Lcom/pawsup/cats/Cat;", "onLoopComplete", "onOutroComplete", "onPetTapped", "scheduleGuestVisit", "setEntranceDuration", "ms", "setOutroDuration", "startCompanionLoop", "random", "Lkotlin/ranges/LongRange;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BreakOverlayViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.data.UserPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.cats.CatRegistry catRegistry = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String catId = null;
    private final int breakMinutes = 0;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pawsup.break_experience.BreakUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pawsup.break_experience.BreakUiState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.pawsup.break_experience.BreakSummary> _finishEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.pawsup.break_experience.BreakSummary> finishEvent = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job timerJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job dialogueJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job guestScheduleJob;
    private long entranceDuration = 5000L;
    private long outroDuration = 5000L;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> dialogueLines;
    private int dialogueIndex = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean companionEntered = null;
    
    @javax.inject.Inject()
    public BreakOverlayViewModel(@org.jetbrains.annotations.NotNull()
    com.pawsup.data.UserPreferences prefs, @org.jetbrains.annotations.NotNull()
    com.pawsup.cats.CatRegistry catRegistry, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedState) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pawsup.break_experience.BreakUiState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.pawsup.break_experience.BreakSummary> getFinishEvent() {
        return null;
    }
    
    public final void onEntranceStarted(@org.jetbrains.annotations.NotNull()
    com.pawsup.cats.Cat cat) {
    }
    
    public final void onEntranceComplete() {
    }
    
    public final void setEntranceDuration(long ms) {
    }
    
    public final void setOutroDuration(long ms) {
    }
    
    private final void startCompanionLoop() {
    }
    
    private final void scheduleGuestVisit() {
    }
    
    public final void dismissGuestVisit() {
    }
    
    private final void onLoopComplete() {
    }
    
    public final void onOutroComplete() {
    }
    
    public final void onPetTapped() {
    }
    
    private final long random(kotlin.ranges.LongRange $this$random) {
        return 0L;
    }
}