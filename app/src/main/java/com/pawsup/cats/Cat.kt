package com.pawsup.cats

data class Cat(
    val id: String,
    val displayName: String,
    val tier: CatTier,
    val backgroundColorHex: String,
    val keyColor: Int,
    val voiceBucket: VoiceBucket,
    val personalityBlurb: String,
    val moodColorHex: String,
    val greetingPitchShift: Float = 1.0f
)

enum class CatTier { FREE, PREMIUM }
enum class VoiceBucket { MISO, YUKI, MOCHA, SHADOW, BISCUIT }
