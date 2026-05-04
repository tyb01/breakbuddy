package com.pawsup.android.ui.overlay

import androidx.annotation.RawRes
import com.pawsup.android.R

/**
 * Break overlay character: WebM intro (once) + WebM idle (looped). No separate audio assets.
 */
interface BreakCharacterAssets {
    @get:RawRes val overlayWebmIntro: Int
    @get:RawRes val overlayWebmIdle: Int
}

object MochiCharacterAssets : BreakCharacterAssets {
    @RawRes override val overlayWebmIntro: Int = R.raw.neko_gate_intro
    @RawRes override val overlayWebmIdle: Int = R.raw.neko_gate_idle
}
