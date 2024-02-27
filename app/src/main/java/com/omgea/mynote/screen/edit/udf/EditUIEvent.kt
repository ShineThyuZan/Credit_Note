package com.omgea.mynote.screen.edit.udf

sealed class EditUIEvent {
    object SaveUser : EditUIEvent()
    object ShowDobPicker : EditUIEvent()
    object HideDobPicker : EditUIEvent()
    data class ShowSnackBar(val message: String) : EditUIEvent()
}