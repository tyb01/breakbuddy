package com.pawsup.break_experience

import com.pawsup.cats.Cat

sealed class BreakState {
    object Entrance      : BreakState()
    object CompanionLoop : BreakState()
    data class GuestVisit(val guestCat: Cat) : BreakState()
    object Outro         : BreakState()
    object Done          : BreakState()
}
