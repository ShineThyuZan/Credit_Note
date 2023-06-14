package com.omgea.mynote.screen.home.components


sealed class HomeEvent {
    object ShowMenu : HomeEvent()
    data class NavigateToEdit(val userId: Int) : HomeEvent()
    object NavigateToCreatePassword : HomeEvent()
}
