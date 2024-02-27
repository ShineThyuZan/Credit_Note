package com.omgea.mynote.screen.home.components

import com.omgea.mynote.model.UserVo


sealed class HomeAction {

    /* object ClickDeleteOk : HomeAction()*/
    object ClickDeleteOk : HomeAction()
    object ClickDeleteCancel : HomeAction()
    data class ClickDelete(val user: UserVo) : HomeAction()

    data class ClickEdit(val user: UserVo) : HomeAction()
    object ClickEditCancel : HomeAction()
    data class ClickEditOk(val user: UserVo) : HomeAction()

    object ClickNewPassword : HomeAction()
    object ClickNewPasswordCancel : HomeAction()
    object ClickNewPasswordOk : HomeAction()
    object ClickLanguage : HomeAction()
    data class ClickActionMore(val user: UserVo) : HomeAction()
    data class PasswordValueChange(val passwordValueChange: String) : HomeAction()
    data class PasswordEditValueChange(val passwordValueChange: String) : HomeAction()
}

sealed class CreatePasswordAction {
    data class ClickUpdatePassword(val password: String) : CreatePasswordAction()
}