package com.eb1xnr007.calme.screen.dashboard

import com.eb1xnr007.calme.core.BaseViewModel
import com.eb1xnr007.calme.screen.dashboard.contract.DashboardIntent
import com.eb1xnr007.calme.screen.dashboard.contract.DashboardUiState
import com.eb1xnr007.calme.screen.dashboard.contract.clearMealDraft
import com.eb1xnr007.calme.screen.dashboard.contract.openEditMeal
import com.eb1xnr007.calme.screen.dashboard.contract.saveMeal
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class DashboardViewModel : BaseViewModel<DashboardUiState, DashboardIntent>(DashboardUiState()) {
    override fun reduce(state: DashboardUiState, intent: DashboardIntent): DashboardUiState =
        when (intent) {
            DashboardIntent.RefreshDate -> {
                val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
                if (today == state.today) state else state.copy(today = today)
            }

            DashboardIntent.OpenAddMeal -> state.copy(isAddMealVisible = true)

            is DashboardIntent.OpenEditMeal -> state.openEditMeal(intent.index)

            is DashboardIntent.RequestDeleteMeal -> {
                if (state.meals.getOrNull(intent.index) ==
                    null
                ) {
                    state
                } else {
                    state.copy(deletingMealIndex = intent.index)
                }
            }

            DashboardIntent.CancelDeleteMeal -> state.copy(deletingMealIndex = null)

            DashboardIntent.ConfirmDeleteMeal -> {
                val index = state.deletingMealIndex
                if (index == null || state.meals.getOrNull(index) == null) {
                    state.copy(deletingMealIndex = null)
                } else {
                    state.copy(
                        meals = state.meals.filterIndexed { mealIndex, _ -> mealIndex != index },
                        deletingMealIndex = null
                    )
                }
            }

            DashboardIntent.DismissAddMeal -> state.clearMealDraft()

            is DashboardIntent.UpdateMealName -> state.copy(mealName = intent.value)

            is DashboardIntent.UpdateCalories -> state.copy(caloriesInput = intent.value)

            is DashboardIntent.UpdateProtein -> state.copy(proteinInput = intent.value)

            DashboardIntent.SaveMeal -> state.saveMeal()
        }
}
