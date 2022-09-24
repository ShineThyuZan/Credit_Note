package com.androidperu.peopleapp.feature_users.presentation.home.components

import com.androidperu.peopleapp.feature_users.domain.model.User
import com.androidperu.peopleapp.feature_users.presentation.home.HomeEvent

sealed class HomeAction {

    data class ClickDelete(val user : User) : HomeAction()
   /* object ClickDeleteOk : HomeAction()*/
    object ClickDeleteOk: HomeAction()
    object ClickDeleteCancel : HomeAction()

    data class PasswordValueChange(val passwordValueChange: String) : HomeAction()

}