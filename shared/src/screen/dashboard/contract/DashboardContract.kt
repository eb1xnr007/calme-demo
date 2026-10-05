package com.eb1xnr007.calme.screen.dashboard.contract

import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class MealUiModel(val name: String, val calories: Int, val protein: Int)

data class DashboardUiState(
    val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val meals: List<MealUiModel> =
        listOf(
            MealUiModel("ข้าวต้มไก่", 340, 24),
            MealUiModel("โยเกิร์ตกรีก + ผลไม้", 260, 19),
            MealUiModel("ข้าวกะเพราไก่", 620, 38)
        ),
    val mealName: String = "",
    val caloriesInput: String = "",
    val proteinInput: String = "",
    val isAddMealVisible: Boolean = false,
    val editingMealIndex: Int? = null,
    val deletingMealIndex: Int? = null
) {
    val todayLabel: String
        get() = "วัน${thaiWeekdays[today.dayOfWeek.ordinal]}ที่ ${today.day} " +
            thaiMonths[today.month.ordinal]
    val consumedCalories: Int get() = meals.sumOf(MealUiModel::calories)
    val consumedProtein: Int get() = meals.sumOf(MealUiModel::protein)
    val deletingMeal: MealUiModel? get() = deletingMealIndex?.let { meals.getOrNull(it) }
    val canSaveMeal: Boolean
        get() = mealName.isNotBlank() &&
            caloriesInput.toIntOrNull()?.let { it >= 0 } == true &&
            proteinInput.toIntOrNull()?.let { it >= 0 } == true
}

sealed interface DashboardIntent {
    data object RefreshDate : DashboardIntent

    data object OpenAddMeal : DashboardIntent

    data class OpenEditMeal(val index: Int) : DashboardIntent

    data class RequestDeleteMeal(val index: Int) : DashboardIntent

    data object CancelDeleteMeal : DashboardIntent

    data object ConfirmDeleteMeal : DashboardIntent

    data object DismissAddMeal : DashboardIntent

    data class UpdateMealName(val value: String) : DashboardIntent

    data class UpdateCalories(val value: String) : DashboardIntent

    data class UpdateProtein(val value: String) : DashboardIntent

    data object SaveMeal : DashboardIntent
}

private val thaiWeekdays =
    listOf("จันทร์", "อังคาร", "พุธ", "พฤหัสบดี", "ศุกร์", "เสาร์", "อาทิตย์")
private val thaiMonths = listOf(
    "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน",
    "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"
)

internal fun DashboardUiState.clearMealDraft() = copy(
    mealName = "",
    caloriesInput = "",
    proteinInput = "",
    isAddMealVisible = false,
    editingMealIndex = null
)

internal fun DashboardUiState.openEditMeal(index: Int): DashboardUiState {
    val meal = meals.getOrNull(index) ?: return this
    return copy(
        mealName = meal.name,
        caloriesInput = meal.calories.toString(),
        proteinInput = meal.protein.toString(),
        isAddMealVisible = true,
        editingMealIndex = index
    )
}

internal fun DashboardUiState.saveMeal(): DashboardUiState {
    val calories = caloriesInput.toIntOrNull() ?: return this
    val protein = proteinInput.toIntOrNull() ?: return this
    if (mealName.isBlank() || calories < 0 || protein < 0) return this

    val meal = MealUiModel(mealName.trim(), calories, protein)
    val updatedMeals = editingMealIndex?.let { index ->
        meals.mapIndexed { itemIndex, item -> if (itemIndex == index) meal else item }
    } ?: (meals + meal)

    return copy(meals = updatedMeals).clearMealDraft()
}
