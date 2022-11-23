package com.omgea.mynote.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omgea.mynote.screen.home.components.HomeAction
import com.omgea.mynote.use_cases.DeleteUserUseCase
import com.omgea.mynote.use_cases.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _homeEvent = MutableSharedFlow<HomeEvent>()
    val homeEvent = _homeEvent.asSharedFlow()

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
                    clearUser = action.user,
                    passwordForDelete = "",
                    isError = false,
                )
                viewModelScope.launch {
                    setDialog(type = DialogType.DELETE)
                }
            }
            HomeAction.ClickDeleteCancel -> {
                _state.value = state.value.copy(
                    passwordForDelete = "",
                    isError = false
                )
                viewModelScope.launch {
                    resetDialog()
                }
            }
            is HomeAction.ClickDeleteOk -> {
                if (state.value.passwordForDelete == "044299") {
                    viewModelScope.launch {
                        _state.value = state.value.copy(
                            isError = false,
                        )
                        resetDialog()
                        deleteUserUseCase(state.value.clearUser)
                    }
                } else {
                    _state.value = state.value.copy(
                        isError = true,
                    )
                }
            }
            is HomeAction.PasswordValueChange -> {
                _state.value = state.value.copy(
                    passwordForDelete = action.passwordValueChange
                )
                /*    error = loginState.value.error.copy(
                        errorPassword = false
                    )*/
            }
            is HomeAction.ClickEdit -> {
                _state.value = state.value.copy(
                    editUser = action.user,
                    passwordForEdit = "",
                    isError = false,
                )
                viewModelScope.launch {
                    setDialog(type = DialogType.EDIT)
                }

            }
            HomeAction.ClickEditCancel -> {
                _state.value = state.value.copy(
                    passwordForEdit = "",
                    isError = false
                )
                viewModelScope.launch {
                    resetDialog()
                }

            }
            is HomeAction.ClickEditOk -> {
                if (state.value.passwordForEdit == "044288") {
                    viewModelScope.launch {
                        _state.value = state.value.copy(
                            isError = false,
                        )
                        _homeEvent.emit(
                            HomeEvent.NavigateToEdit(userId = action.user.id!!)
                        )
                        resetDialog()
                    }

                } else {
                    _state.value = state.value.copy(
                        isError = true,
                    )
                }
            }
            is HomeAction.PasswordEditValueChange -> {
                _state.value = state.value.copy(
                    passwordForEdit = action.passwordValueChange
                )
            }
            is HomeAction.ClickActionMore -> {
                _state.value = state.value.copy(
                    clearUser = action.user,
                    editUser = action.user
                )
                viewModelScope.launch {
                    _homeEvent.emit(
                        HomeEvent.ShowMenu
                    )
                }
            }
        }
    }

    private fun resetDialog() {
        _state.value = state.value.copy(
            dialogType = DialogType.NOTHING,
            showDialog = false
        )
    }

    private fun setDialog(type: DialogType) {
        _state.value = state.value.copy(
            dialogType = type,
            showDialog = true
        )
    }
}