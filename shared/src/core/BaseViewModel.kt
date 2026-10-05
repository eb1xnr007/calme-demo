package com.eb1xnr007.calme.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<State : Any, Intent : Any>(initialState: State) : ViewModel() {
    var uiState by mutableStateOf(initialState)
        private set

    fun onIntent(intent: Intent) {
        uiState = reduce(uiState, intent)
    }

    protected abstract fun reduce(state: State, intent: Intent): State
}
