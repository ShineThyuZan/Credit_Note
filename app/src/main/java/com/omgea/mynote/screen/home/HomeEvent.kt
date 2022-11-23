package com.omgea.mynote.screen.home


sealed class HomeEvent {
    object ShowMenu : HomeEvent()
    data class NavigateToEdit(val userId: Int) : HomeEvent()

}
