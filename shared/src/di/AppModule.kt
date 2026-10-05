package com.eb1xnr007.calme.di

import com.eb1xnr007.calme.AppViewModel
import com.eb1xnr007.calme.screen.dashboard.DashboardViewModel
import com.eb1xnr007.calme.screen.goals.GoalsViewModel
import com.eb1xnr007.calme.screen.reports.ReportsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::ReportsViewModel)
    viewModelOf(::GoalsViewModel)
}
