package com.omgea.mynote.screen.home.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omgea.mynote.screen.home.sheet_view.LanguageViewModelState
import com.omgea.mynote.use_cases.DeleteUserUseCase
import com.omgea.mynote.use_cases.GetUserListUseCase
import com.omgea.mynote.use_cases.GetUsersUseCase
import com.omgea.mynote.use_cases.LocalPullUseCase
import com.omgea.mynote.use_cases.LocalePutUseCase
import com.omgea.mynote.use_cases.PasswordPullUseCase
import com.omgea.mynote.use_cases.PasswordPutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    getUserListUseCase: GetUserListUseCase,
    getUsersUseCase: GetUsersUseCase,
    private val pullLocalUseCase: LocalPullUseCase,
    private val putLocaleUseCase: LocalePutUseCase,
    private val passwordPutUseCase: PasswordPutUseCase,
    private val passwordPullUseCase: PasswordPullUseCase,
) : ViewModel() {

    private val vmState = MutableStateFlow(LanguageViewModelState())
/*    val locale = vmState
        .map(LanguageViewModelState::asLocale)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.asLocale()
        )
    val shouldShowLoading = vmState
        .map(LanguageViewModelState::asLoading)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.asLoading()
        )*/

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _homeEvent = MutableSharedFlow<HomeEvent>()
    val homeEvent = _homeEvent.asSharedFlow()

    /*
        init {
            getUserListUseCase().onEach { usersList ->
                _state.value = state.value.copy(
                    usersList = usersList.asReversed()
                )
            }.launchIn(viewModelScope)

            getPasswordFormDataStore()
        }
    */

    init {
        getUsersUseCase().onEach { users ->
            _state.value = state.value.copy(
                usersList = users
            )
        }.launchIn(viewModelScope)
        getPasswordFormDataStore()
        getAppLocale()
    }

    private fun getAppLocale() {
        viewModelScope.launch {
            pullLocalUseCase().collect {
                _state.value = state.value.copy(
                    isDefaultLocal = it
                )
            }
           // Log.d("isLocale.pull", state.value.isDefaultLocal.toString())
        }
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
                if (state.value.password == state.value.passwordFormDs) {
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

            HomeAction.ClickLanguage -> {
                viewModelScope.launch {
                    setDialog(type = DialogType.LANGUAGE_DIALOG)
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

    fun updateLocale(isDefault: Boolean) {
        Log.d("isLocale.choose", isDefault.toString())
        viewModelScope.launch {
            putLocaleUseCase.invoke(
                isDefaultLocale = isDefault
            )
            resetDialog()
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