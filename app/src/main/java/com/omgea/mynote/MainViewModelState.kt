package com.omgea.mynote

import com.omgea.mynote.util.LocaleType

data class MainViewModelState(
    val appLocale: LocaleType = LocaleType.English,
){
    fun asAppLocale() = appLocale
}