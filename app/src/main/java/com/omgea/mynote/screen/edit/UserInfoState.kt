package com.omgea.mynote.screen.edit

data class UserInfoState(
    val error: EditError = EditError(),
    val userName: String? = "",
    val lastName: String? = "",
    val age: String? = "",
    val date: String? = "",
    val isEnable: Boolean? = false,
    val isSomethingEdited: Boolean = false,
)
