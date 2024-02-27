package com.omgea.mynote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omgea.mynote.use_cases.LocalPullUseCase
import com.omgea.mynote.util.LocaleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: LocalPullUseCase,
) : ViewModel() {
    private val vmState = MutableStateFlow(MainViewModelState())

    val appLocale = vmState
        .map(MainViewModelState::asAppLocale)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.asAppLocale(),
        )
    init {
        getLocale()
    }

    private fun getLocale() {
        viewModelScope.launch {
            useCase().collect {
                vmState.update { state ->
                    state.copy(
                        appLocale = if (it) LocaleType.English else LocaleType.Myanmar,
                    )
                }
            }
        }
    }
}