package com.omgea.mynote.screen.edit

sealed class EditAction {
    data class EnteredName(val nameText: String) : EditAction()
    data class EnteredDescription(val descText: String) : EditAction()
    data class EnterAmount(val amountText: String) : EditAction()
    data class ChangeDob(val dob: String) : EditAction()
    object ClickDobPicker : EditAction()
    object InsertUser : EditAction()
    object ClickItemMore : EditAction()
}