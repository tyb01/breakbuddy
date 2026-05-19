package com.pawsup.break_experience;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.activity.ComponentActivity;
import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.core.view.WindowCompat;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatRegistry;
import com.pawsup.break_experience.components.*;
import com.pawsup.paywall.PaywallActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001ad\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u00a8\u0006\r"}, d2 = {"BreakOverlayScreen", "", "state", "Lcom/pawsup/break_experience/BreakUiState;", "cat", "Lcom/pawsup/cats/Cat;", "onEntranceComplete", "Lkotlin/Function0;", "onOutroComplete", "onPet", "onGuestMeet", "Lkotlin/Function1;", "onGuestDismiss", "app_debug"})
public final class BreakOverlayActivityKt {
    
    @androidx.compose.runtime.Composable()
    private static final void BreakOverlayScreen(com.pawsup.break_experience.BreakUiState state, com.pawsup.cats.Cat cat, kotlin.jvm.functions.Function0<kotlin.Unit> onEntranceComplete, kotlin.jvm.functions.Function0<kotlin.Unit> onOutroComplete, kotlin.jvm.functions.Function0<kotlin.Unit> onPet, kotlin.jvm.functions.Function1<? super com.pawsup.cats.Cat, kotlin.Unit> onGuestMeet, kotlin.jvm.functions.Function0<kotlin.Unit> onGuestDismiss) {
    }
}