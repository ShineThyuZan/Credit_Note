package com.omgea.mynote.screen.home.sheet_view

import com.omgea.mynote.util.LocaleType

data class LanguageViewModelState (
    val appLocale: LocaleType = LocaleType.English,
    val loading: Pair<LocaleType, Boolean> = Pair(LocaleType.English, false)
) {
    fun asLocale() = appLocale
    fun asLoading() = loading
}