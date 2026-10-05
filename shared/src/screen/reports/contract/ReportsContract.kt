package com.eb1xnr007.calme.screen.reports.contract

enum class ReportPeriod(val label: String) {
    Day("วัน"),
    Week("สัปดาห์"),
    Month("เดือน")
}

data class ReportsUiState(val period: ReportPeriod = ReportPeriod.Day)

sealed interface ReportsIntent {
    data class SelectPeriod(val period: ReportPeriod) : ReportsIntent
}
