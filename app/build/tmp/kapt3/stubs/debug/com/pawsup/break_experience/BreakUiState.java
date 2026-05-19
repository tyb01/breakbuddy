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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001b\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000eJ\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0007H\u00c6\u0003J\t\u0010 \u001a\u00020\fH\u00c6\u0003J\t\u0010!\u001a\u00020\u0007H\u00c6\u0003JQ\u0010\"\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010#\u001a\u00020\f2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\u0007H\u00d6\u0001J\t\u0010&\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016\u00a8\u0006\'"}, d2 = {"Lcom/pawsup/break_experience/BreakUiState;", "", "cat", "Lcom/pawsup/cats/Cat;", "breakState", "Lcom/pawsup/break_experience/BreakState;", "remainingSeconds", "", "currentDialogueLine", "", "sessionPetCount", "showPetHint", "", "totalBreaks", "(Lcom/pawsup/cats/Cat;Lcom/pawsup/break_experience/BreakState;ILjava/lang/String;IZI)V", "getBreakState", "()Lcom/pawsup/break_experience/BreakState;", "getCat", "()Lcom/pawsup/cats/Cat;", "getCurrentDialogueLine", "()Ljava/lang/String;", "getRemainingSeconds", "()I", "getSessionPetCount", "getShowPetHint", "()Z", "getTotalBreaks", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class BreakUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.pawsup.cats.Cat cat = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pawsup.break_experience.BreakState breakState = null;
    private final int remainingSeconds = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String currentDialogueLine = null;
    private final int sessionPetCount = 0;
    private final boolean showPetHint = false;
    private final int totalBreaks = 0;
    
    public BreakUiState(@org.jetbrains.annotations.Nullable()
    com.pawsup.cats.Cat cat, @org.jetbrains.annotations.NotNull()
    com.pawsup.break_experience.BreakState breakState, int remainingSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String currentDialogueLine, int sessionPetCount, boolean showPetHint, int totalBreaks) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pawsup.cats.Cat getCat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.break_experience.BreakState getBreakState() {
        return null;
    }
    
    public final int getRemainingSeconds() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentDialogueLine() {
        return null;
    }
    
    public final int getSessionPetCount() {
        return 0;
    }
    
    public final boolean getShowPetHint() {
        return false;
    }
    
    public final int getTotalBreaks() {
        return 0;
    }
    
    public BreakUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.pawsup.cats.Cat component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.break_experience.BreakState component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pawsup.break_experience.BreakUiState copy(@org.jetbrains.annotations.Nullable()
    com.pawsup.cats.Cat cat, @org.jetbrains.annotations.NotNull()
    com.pawsup.break_experience.BreakState breakState, int remainingSeconds, @org.jetbrains.annotations.NotNull()
    java.lang.String currentDialogueLine, int sessionPetCount, boolean showPetHint, int totalBreaks) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}