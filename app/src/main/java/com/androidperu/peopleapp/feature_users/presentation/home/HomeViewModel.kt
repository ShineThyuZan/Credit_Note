package com.androidperu.peopleapp.feature_users.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidperu.peopleapp.feature_users.domain.use_cases.DeleteUserUseCase
import com.androidperu.peopleapp.feature_users.domain.use_cases.GetUserListUseCase
import com.androidperu.peopleapp.feature_users.presentation.home.components.HomeAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getUserListUseCase().onEach { usersList ->
            _state.value = state.value.copy(
                usersList = usersList
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ClickDelete -> {
                _state.value = state.value.copy(
                    shouldShowRemoveDialog = true
                )
                _state.value = state.value.copy(
                    clearUser = action.user
                )
            }
            HomeAction.ClickDeleteCancel -> {
                _state.value = state.value.copy(
                    shouldShowRemoveDialog = false
                )
            }
            is HomeAction.ClickDeleteOk -> {
                if (state.value.password == "password") {
                    viewModelScope.launch {
                        _state.value = state.value.copy(
                            isError = false
                        )
                        deleteUserUseCase(state.value.clearUser)
                    }
                    _state.value = state.value.copy(
                        shouldShowRemoveDialog = false
                    )
                } else {
                    _state.value = state.value.copy(
                        errorText = "Wrong Password"
                    )
                    _state.value = state.value.copy(
                        isError = true
                    )

                }
            }
            is HomeAction.PasswordValueChange -> {
                _state.value = state.value.copy(
                    password = action.passwordValueChange
                )
                /*    error = loginState.value.error.copy(
                        errorPassword = false
                    )*/
            }
        }
    }
}