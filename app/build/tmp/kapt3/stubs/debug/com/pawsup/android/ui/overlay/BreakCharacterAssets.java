package com.pawsup.android.ui.overlay;

import androidx.annotation.RawRes;
import com.pawsup.android.R;

/**
 * Break overlay character: WebM intro (once) + WebM idle (looped). No separate audio assets.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\u00a8\u0006\b"}, d2 = {"Lcom/pawsup/android/ui/overlay/BreakCharacterAssets;", "", "overlayWebmIdle", "", "getOverlayWebmIdle", "()I", "overlayWebmIntro", "getOverlayWebmIntro", "app_debug"})
public abstract interface BreakCharacterAssets {
    
    @androidx.annotation.RawRes()
    public abstract int getOverlayWebmIntro();
    
    @androidx.annotation.RawRes()
    public abstract int getOverlayWebmIdle();
}