package com.pawsup.android.data.datastore

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        fun fromOrdinal(value: Int?): ThemeMode = when (value) {
            LIGHT.ordinal -> LIGHT
            DARK.ordinal -> DARK
            else -> SYSTEM
        }
    }
}
