package com.eb1xnr007.calme.screen.goals

import com.eb1xnr007.calme.core.BaseViewModel
import com.eb1xnr007.calme.screen.goals.contract.GoalsIntent
import com.eb1xnr007.calme.screen.goals.contract.GoalsUiState

class GoalsViewModel : BaseViewModel<GoalsUiState, GoalsIntent>(GoalsUiState()) {
    override fun reduce(state: GoalsUiState, intent: GoalsIntent): GoalsUiState = when (intent) {
        GoalsIntent.Save -> if (state.canSave) {
            state.copy(
                savedWeight = state.weight.toDouble(),
                savedHeight = state.height.toDouble(),
                savedGoal = state.goal
            )
        } else {
            state
        }

        GoalsIntent.ToggleNotifications -> state.copy(
            notificationsEnabled = !state.notificationsEnabled
        )

        GoalsIntent.OpenGoalMenu -> state.copy(isGoalMenuVisible = true)

        GoalsIntent.DismissGoalMenu -> state.copy(isGoalMenuVisible = false)

        is GoalsIntent.SelectGoal -> state.copy(goal = intent.goal, isGoalMenuVisible = false)

        is GoalsIntent.UpdateWeight -> state.copy(weight = intent.value)

        is GoalsIntent.UpdateHeight -> state.copy(height = intent.value)

        is GoalsIntent.UpdateCalorieGoal -> state.copy(calorieGoal = intent.value)

        is GoalsIntent.UpdateProteinGoal -> state.copy(proteinGoal = intent.value)
    }
}
