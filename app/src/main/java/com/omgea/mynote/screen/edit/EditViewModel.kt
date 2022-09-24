package com.omgea.mynote.screen.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.use_cases.GetUserUseCase
import com.omgea.mynote.use_cases.InsertUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(UserInfoState())
    val state: State<UserInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentUserId: Int? = null

    init {
        savedStateHandle.get<Int>("userId")?.let { userId ->
            if (userId != -1) {
                viewModelScope.launch {
                    getUserUseCase(userId)?.also { user ->
                        currentUserId = user.id
                        _state.value = state.value.copy(
                            userName = user.name,
                            lastName = user.lastName,
                            age = user.age.toString()
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: EditAction) {
        when (action) {
            is EditAction.EnteredName -> {
                _state.value = state.value.copy(
                    userName = action.nameText
                )
            }
            is EditAction.EnteredDescription -> {
                _state.value = state.value.copy(
                    lastName = action.descText
                )
            }
            is EditAction.EnterAmount -> {
                _state.value = state.value.copy(
                    age = action.amountText
                )
                if (action.amountText == "" || state.value.userName == "") {
                    _state.value = state.value.copy(
                        isEnable = false
                    )
                } else {
                    _state.value = state.value.copy(
                        isEnable = true
                    )
                }

            }
            EditAction.InsertUser -> {
                viewModelScope.launch {
                    insertUserUseCase(
                        UserVo(
                            name = state.value.userName!!,
                            lastName = state.value.lastName!!,
                            age = state.value.age!!.toInt(),
                            id = currentUserId
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveUser)
                }
            }
        }
    }

    sealed class UiEvent {
        object SaveUser : UiEvent()
    }
}