package com.omgea.mynote.util

import java.util.Locale

sealed class LocaleType(val locale: Locale) {
    object English : LocaleType(locale = Locale.ENGLISH)
    object Myanmar : LocaleType(locale = Locale.getDefault())
}

fun LocaleType.toLocaleType(): LocaleType {
    return when (this) {
        LocaleType.English -> LocaleType.English
        LocaleType.Myanmar -> LocaleType.Myanmar
        else -> LocaleType.English
    }
}