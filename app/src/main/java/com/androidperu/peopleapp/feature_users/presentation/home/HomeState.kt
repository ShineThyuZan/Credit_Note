package com.androidperu.peopleapp.feature_users.presentation.home

import com.androidperu.peopleapp.feature_users.domain.model.User

data class HomeState(
    val usersList: List<User> = emptyList(),
    val shouldShowRemoveDialog: Boolean? = false,
    val clearUser: User = User(),
    val password: String = "",
    val isError: Boolean = false,
    val errorText: String = "",
)
