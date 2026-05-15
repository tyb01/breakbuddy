package com.pawsup.cats

object CatVoiceProvider {

    private val misoLoop = listOf(
        "You smell tired. Just an observation.", "I'm not judging. (I am a little.)",
        "Your thumb's been busy. Give it a minute.", "Stay. The view from here is fine.",
        "You're allowed to do nothing.", "Hands down. Both of them.",
        "I came all the way here, you know.", "Look at me instead. I'm better.",
        "Breath in. Breath out. There you go.", "Whatever it was, it'll still be there.",
        "We don't have to talk. I prefer it actually.", "Pet me if you're nervous. I'll allow it.",
        "You're doing fine. Probably.", "This is the cat appointment. Mandatory.",
        "Soft moment. Don't ruin it.", "Take up less space in your head.",
        "I came to remind you you're a person.", "Eyes off the rectangle for a second.",
        "You didn't miss anything important.", "We're just resting. That's the whole plan.",
        "Slow down. I dare you.", "Look out a window after this.",
        "You blink less than you should.", "I'll wait. I have nothing else on.",
        "This is the part where you exhale.", "I love you. Don't make it weird."
    )

    private val yukiLoop = listOf(
        "Breathe with me. Slowly. Like this.", "Have you had water today? Real water?",
        "There's no rush. Truly.", "I'll stay until you feel like yourself again.",
        "Close your eyes for a moment. Just a moment.", "You're allowed to slow down.",
        "Whatever it is, it can wait for you.", "I came because I missed you.",
        "Soft breath in. Soft breath out.", "I think you're doing your best.",
        "Unclench your hands, darling.", "We have all the time we need.",
        "You looked tired. So I came.", "Step away from your day for a minute.",
        "Nothing urgent will happen in five minutes.", "Your body is asking for stillness.",
        "Slow your eyes. Look at one thing.", "Tell yourself one kind thing.",
        "Stretch your back when I leave.", "You're loved. Just so you know.",
        "Pet my fur. It'll help us both.", "Sit a little longer with me.",
        "There. That's better already.", "You came back. That's enough.", "Rest now. I'm here."
    )

    private val mochaLoop = listOf(
        "Okay okay okay sit sit sit.", "I have so much to tell you. I forgot it.",
        "Watch my tail. WATCH IT.", "You smell like outside. Were you outside?",
        "I just remembered something! Now I forgot.", "Wiggle. Wiggle wiggle wiggle.",
        "I'm doing the loaf now. The loaf!", "Pet me pet me pet me.",
        "Hi again. Did you miss me? Of course.", "I am very small but very important.",
        "What are we doing? Are we doing it?", "I love being here. I love being here.",
        "Look at my paws! Tiny!", "Boop. (I booped you.)",
        "Are you my friend now? Yes? Yes.", "I'm purring. Can you hear it? Listen.",
        "I will protect you from the air.", "You have a nose. I have a nose too!",
        "This is the best break. The best.", "I can do a thing. Look. Watching?",
        "Slow blink at you. Slow. Blink.", "I'm a tiny mighty cat. Mighty!",
        "Stay stay stay. Five more minutes!", "Are you having fun? I'm having fun.",
        "You found me! Well, I found you.", "Best human. Confirmed. Best."
    )

    private val shadowLoop = listOf(
        "Nothing important is happening on that feed.", "Be where your paws are.",
        "The mind that is not racing arrives sooner.", "Stillness is a skill. You're practicing.",
        "Notice your breath. Don't change it. Just notice.", "The hurry is a story. It isn't true.",
        "You are not what you scrolled.", "Watch the air around your hands.",
        "The world will continue without your attention.", "Empty mind is full of room.",
        "Soften the place behind your eyes.", "The body knows. Ask it.",
        "You don't owe the screen anything.", "I sit. The room sits. You sit.",
        "Time is wider than you think.", "The wave passes. So does the urge.",
        "Your thoughts are not your home.", "Breath in: here. Breath out: now.",
        "The cat does not check the clock.", "What if you simply rest.",
        "Stop looking. Start noticing.", "The quiet has been waiting.",
        "Where are your shoulders right now.", "Even the river rests in pools.",
        "You are enough without doing.", "The eye that is rested sees better.",
        "Listen past the noise.", "Return to the breath. Return again.", "Soft attention. Soft."
    )

    private val biscuitLoop = listOf(
        "Let's pretend this is a tea break.", "Stretch your back. I'll wait.",
        "Have a snack after this. Promise me.", "Your hands are tight. Open them.",
        "Five minutes is a perfectly good amount.", "You worked hard. I noticed.",
        "Take up some space. Lean back.", "Warm drink later? Yes, warm drink.",
        "Soft is a feeling. Try some.", "I made room. It's for you.",
        "Has anyone told you you're doing fine?", "Soup tonight, maybe. Just a thought.",
        "Your eyes need a longer blink.", "Roll your wrists. Both ways. There.",
        "You're allowed to enjoy this.", "I packed extra coziness. We can share.",
        "Even cats nap. Don't fight it.", "Step outside later. Just a little.",
        "Tell your shoulders the day is done.", "I'm proud of you. Don't argue.",
        "Want a snack? I want a snack.", "Soft sweater energy. Match me.",
        "The good news is: you can rest.", "Five deep breaths before you go.",
        "We made it to right now together.", "Be gentle with the next hour.",
        "I love your face. Just saying.", "Cozy is a verb. Do it now."
    )

    private val entrances = mapOf(
        VoiceBucket.MISO    to "Hey. Came to sit with you.",
        VoiceBucket.YUKI    to "There you are. Come rest a moment, sweetheart.",
        VoiceBucket.MOCHA   to "HI HI HI you're here!",
        VoiceBucket.SHADOW  to "The scroll is a river. Step out a moment.",
        VoiceBucket.BISCUIT to "Oh good, you're here. Sit, sit."
    )

    private val goodbyes = mapOf(
        VoiceBucket.MISO    to "Okay. Go gently.",
        VoiceBucket.YUKI    to "Sleep well later. I mean it.",
        VoiceBucket.MOCHA   to "Bye!! Come back soon!! Bye!!",
        VoiceBucket.SHADOW  to "We meet again at the next bend.",
        VoiceBucket.BISCUIT to "Go have a snack. That's an order."
    )

    private val loops = mapOf(
        VoiceBucket.MISO    to misoLoop,
        VoiceBucket.YUKI    to yukiLoop,
        VoiceBucket.MOCHA   to mochaLoop,
        VoiceBucket.SHADOW  to shadowLoop,
        VoiceBucket.BISCUIT to biscuitLoop
    )

    fun getEntrance(bucket: VoiceBucket): String {
        val line = entrances[bucket] ?: "Hey."
        assert(line.trim().split("\\s+".toRegex()).size <= 12) { "Entrance line exceeds 12 words: $line" }
        return line
    }

    fun getGoodbye(bucket: VoiceBucket): String {
        val line = goodbyes[bucket] ?: "See you."
        assert(line.trim().split("\\s+".toRegex()).size <= 12) { "Goodbye line exceeds 12 words: $line" }
        return line
    }

    fun getLoopLines(bucket: VoiceBucket): List<String> = loops[bucket] ?: misoLoop

    fun getGuestAdverb(catId: String): String = when (catId) {
        "yuki"    -> "sleepily"
        "mocha"   -> "eagerly"
        "shadow"  -> "mysteriously"
        "biscuit" -> "warmly"
        else      -> "curiously"
    }
}
