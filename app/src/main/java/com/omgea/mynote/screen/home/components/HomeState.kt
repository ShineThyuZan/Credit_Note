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

    val passwordForDelete: String = "",
    val passwordForEdit: String = "",
    val isError: Boolean = false,
)

enum class DialogType {
    NOTHING,
    EDIT,
    DELETE,
}