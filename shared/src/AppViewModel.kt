package com.eb1xnr007.calme

import com.eb1xnr007.calme.component.AppPage
import com.eb1xnr007.calme.core.BaseViewModel

data class AppUiState(val page: AppPage = AppPage.Today)

sealed interface AppIntent {
    data class SelectPage(val page: AppPage) : AppIntent
}

class AppViewModel : BaseViewModel<AppUiState, AppIntent>(AppUiState()) {
    override fun reduce(state: AppUiState, intent: AppIntent): AppUiState = when (intent) {
        is AppIntent.SelectPage -> state.copy(page = intent.page)
    }
}
