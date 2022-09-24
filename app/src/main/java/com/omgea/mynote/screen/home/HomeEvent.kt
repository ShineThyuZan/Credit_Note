package com.omgea.mynote.screen.home


sealed class HomeEvent {
    data class NavigateToEdit(val userId : Int) : HomeEvent()
}
