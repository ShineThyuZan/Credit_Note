package com.androidperu.peopleapp.feature_users.presentation.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidperu.peopleapp.feature_users.domain.model.User
import com.androidperu.peopleapp.feature_users.domain.use_cases.GetUserUseCase
import com.androidperu.peopleapp.feature_users.domain.use_cases.InsertUserUseCase
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

    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.EnteredName -> {
                _state.value = state.value.copy(
                    userName = event.value
                )
            }
            is EditEvent.EnteredDescription -> {
                _state.value = state.value.copy(
                    lastName = event.value
                )
            }
            is EditEvent.EnterAmount -> {
                _state.value = state.value.copy(
                    age = event.value
                )
            }
            EditEvent.InsertUser -> {
                viewModelScope.launch {
                    insertUserUseCase(
                        User(
                            name = state.value.userName,
                            lastName = state.value.lastName,
                            age = state.value.age.toInt(),
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