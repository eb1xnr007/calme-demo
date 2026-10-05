package com.eb1xnr007.calme.screen.reports

import com.eb1xnr007.calme.core.BaseViewModel
import com.eb1xnr007.calme.screen.reports.contract.ReportsIntent
import com.eb1xnr007.calme.screen.reports.contract.ReportsUiState

class ReportsViewModel : BaseViewModel<ReportsUiState, ReportsIntent>(ReportsUiState()) {
    override fun reduce(state: ReportsUiState, intent: ReportsIntent): ReportsUiState =
        when (intent) {
            is ReportsIntent.SelectPeriod -> state.copy(period = intent.period)
        }
}
