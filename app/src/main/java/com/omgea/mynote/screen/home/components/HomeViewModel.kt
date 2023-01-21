package com.omgea.mynote.screen.home.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omgea.mynote.use_cases.PasswordPullUseCase
import com.omgea.mynote.use_cases.PasswordPutUseCase
import com.omgea.mynote.use_cases.DeleteUserUseCase
import com.omgea.mynote.use_cases.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    getUserListUseCase: GetUserListUseCase,
    private val passwordPutUseCase: PasswordPutUseCase,
    private val passwordPullUseCase: PasswordPullUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _homeEvent = MutableSharedFlow<HomeEvent>()
    val homeEvent = _homeEvent.asSharedFlow()

    init {
        getUserListUseCase().onEach { usersList ->
            _state.value = state.value.copy(
                usersList = usersList.asReversed()
            )
        }.launchIn(viewModelScope)

        getPasswordFormDataStore()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ClickDelete -> {
                _state.value = state.value.copy(
                    clearUser = action.user,
                    password = "",
                    isError = false,
                )
                viewModelScope.launch {
                    setDialog(type = DialogType.DELETE)
                }
            }
            HomeAction.ClickDeleteCancel -> {
                _state.value = state.value.copy(
                    isError = false
                )
                viewModelScope.launch {
                    resetDialog()
                }
            }
            is HomeAction.ClickDeleteOk -> {
                if (state.value.password == state.value.passwordFormDs) {
                    viewModelScope.launch {
                        _state.value = state.value.copy(
                            password = "",
                            isError = false,
                        )
                        resetDialog()
                        deleteUserUseCase(state.value.clearUser)
                    }
                } else {
                    _state.value = state.value.copy(
                        password = "",
                        isError = true,
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
            is HomeAction.ClickEdit -> {
                _state.value = state.value.copy(
                    editUser = action.user,
                    password = "",
                    isError = false,
                )
                viewModelScope.launch {
                    setDialog(type = DialogType.EDIT)
                }
            }
            HomeAction.ClickEditCancel -> {
                _state.value = state.value.copy(
                    password = "",
                    isError = false
                )
                viewModelScope.launch {
                    resetDialog()
                }
            }
            is HomeAction.ClickEditOk -> {
                if (state.value.password == state.value.actionPassword) {
                    viewModelScope.launch {
                        _state.value = state.value.copy(
                            isError = false,
                            password = "",
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
                    password = action.passwordValueChange
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
            HomeAction.ClickNewPassword -> {
                viewModelScope.launch {
                    setDialog(type = DialogType.NEW_PASSWORD)
                }
            }
            HomeAction.ClickNewPasswordCancel -> {
                _state.value = state.value.copy(
                    password = "",
                    isError = false
                )
                viewModelScope.launch {
                    resetDialog()
                }
            }
            is HomeAction.ClickNewPasswordOk -> {
                if (state.value.password == state.value.passwordFormDs) {
                    _state.value = state.value.copy(
                        password = "",
                        isError = false
                    )
                    viewModelScope.launch {
                      resetDialog()
                        _homeEvent.emit(
                            HomeEvent.NavigateToCreatePassword
                        )
                    }
                } else {
                    _state.value = state.value.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun onCreatePasswordAction(action: CreatePasswordAction) {
        when (action) {
            is CreatePasswordAction.ClickUpdatePassword -> {
                _state.value = state.value.copy(
                    actionPassword = action.password
                )
                // put password to DataStore
                viewModelScope.launch {
                    passwordPutUseCase.invoke(state.value.actionPassword)
                }
            }
        }
    }

    // pull password form data store
    private fun getPasswordFormDataStore() {
        viewModelScope.launch {

            passwordPullUseCase.invoke()::collectLatest{ passwordFormDs ->
                _state.value = state.value.copy(
                    passwordFormDs = passwordFormDs
                )

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