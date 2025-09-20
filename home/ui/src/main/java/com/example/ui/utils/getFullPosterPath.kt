package com.example.ui.utils

import java.util.Locale

fun String.getFullPosterPath(): String {
    return "https://image.tmdb.org/t/p/w500$this"
}

fun Double.roundToOneDecimal(): String {
    return String.format(Locale.ENGLISH, "%.1f", this)
}