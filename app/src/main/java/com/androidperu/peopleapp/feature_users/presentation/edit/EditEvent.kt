package com.androidperu.peopleapp.feature_users.presentation.edit

sealed class EditEvent {
    data class EnteredName(val value: String): EditEvent()
    data class EnteredDescription(val value: String): EditEvent()
    data class EnterAmount(val value: String): EditEvent()
    object InsertUser: EditEvent()
}