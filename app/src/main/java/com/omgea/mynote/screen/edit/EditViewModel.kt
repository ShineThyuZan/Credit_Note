package com.omgea.mynote.screen.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omgea.mynote.common.DateTimeUtil
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
    private val validateUseCase: ValidateEditUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(UserInfoState())
    val state: State<UserInfoState> = _state

    private val _eventFlow = MutableSharedFlow<EditUIEvent>()
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
                            age = user.age.toString(),
                            date = user.date
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
                validateForUpdate()
            }
            EditAction.ClickDobPicker -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        EditUIEvent.ShowDobPicker
                    )
                }
            }
            is EditAction.ChangeDob -> {
                _state.value = state.value.copy(
                    date = action.dob,
                    isSomethingEdited = true
                )
                viewModelScope.launch {
                    _eventFlow.emit(
                        EditUIEvent.HideDobPicker
                    )
                }
            }
        }
    }

    private fun validateForUpdate() {
        val dobLong =
            DateTimeUtil.getMilliFromDate(state.value.date)
        validateUseCase.invoke(
            dob = dobLong
        ).apply {
            _state.value = state.value.copy(
                error = this
            )
        }.also {
            if (!state.value.isSomethingEdited) {
                viewModelScope.launch {
                    _eventFlow.emit(EditUIEvent.ShowSnackBar(message = "ဘာမှမပြောင်းလဲပါ !"))
                }
                return
            }
            if (!it.isErrorDob) {
                updateInfoToDB()
            }
        }
    }

    private fun updateInfoToDB() {
        viewModelScope.launch {
            insertUserUseCase(
                UserVo(
                    name = state.value.userName!!,
                    lastName = state.value.lastName!!,
                    age = state.value.age!!.toInt(),
                    id = currentUserId,
                    date = state.value.date!!
                )
            )
            _eventFlow.emit(EditUIEvent.SaveUser)
        }
    }


    sealed class EditUIEvent {
        object SaveUser : EditUIEvent()
        object ShowDobPicker : EditUIEvent()
        object HideDobPicker : EditUIEvent()

        data class ShowSnackBar(val message: String) : EditUIEvent()
    }
}