package com.omgea.mynote.screen.home.components

import com.omgea.mynote.model.UserVo

data class HomeState(
    val usersList: List<UserVo> = emptyList(),

    /*    val shouldShowRemoveDialog: Boolean? = false,
        val shouldShowEditDialog: Boolean? = false,*/

    val showDialog: Boolean = false,
    val dialogType: DialogType = DialogType.NOTHING,

    val clearUser: UserVo = UserVo(),
    val editUser: UserVo = UserVo(),

    // action pw to save
    val actionPassword: String = "",
    val password: String = "",
    val passwordFormDs: String = "",

    val isError: Boolean = false,

    val isDefaultLocal : Boolean = true
)

enum class DialogType {
    NOTHING,
    EDIT,
    DELETE,
    NEW_PASSWORD,
    LANGUAGE_DIALOG,
}