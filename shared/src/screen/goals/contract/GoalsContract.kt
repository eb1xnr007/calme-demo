package com.eb1xnr007.calme.screen.goals.contract

import kotlin.math.roundToInt

data class GoalsUiState(
    val goal: String = "คงน้ำหนัก",
    val weight: String = "67",
    val height: String = "170",
    val calorieGoal: String = "2200",
    val proteinGoal: String = "120",
    val isGoalMenuVisible: Boolean = false,
    val notificationsEnabled: Boolean = false,
    val savedWeight: Double = 67.0,
    val savedHeight: Double = 170.0,
    val savedGoal: String = "คงน้ำหนัก"
) {
    val canSave: Boolean
        get() = weight.toDoubleOrNull()?.let { it > 0 } == true &&
            height.toDoubleOrNull()?.let { it > 0 } == true &&
            calorieGoal.toIntOrNull()?.let { it > 0 } == true &&
            proteinGoal.toIntOrNull()?.let { it > 0 } == true

    private val bmi: Double get() = savedWeight / ((savedHeight / 100.0) * (savedHeight / 100.0))
    val bmiValue: String get() = (bmi * 10).roundToInt().div(10.0).toString()
    val bmiLabel: String
        get() = when {
            bmi < 18.5 -> "น้ำหนักน้อย"
            bmi < 23.0 -> "สมส่วน"
            bmi < 25.0 -> "เริ่มเกินเกณฑ์"
            else -> "น้ำหนักเกิน"
        }
    val bmiTitle: String
        get() = when (savedGoal) {
            "ลดน้ำหนัก" -> "เน้นลดน้ำหนักอย่างค่อยเป็นค่อยไป"
            "เพิ่มกล้ามเนื้อ" -> "เน้นเพิ่มกล้ามเนื้อพร้อมโปรตีนให้ถึง"
            else -> "คงน้ำหนักด้วยมื้อที่สมดุล"
        }
    val bmiCopy: String
        get() =
            "ผลประเมิน BMI: $bmiLabel. " +
                "ใช้เป้าหมายแคลอรี่และโปรตีนเป็นแนวทาง ไม่ใช่การวินิจฉัยทางการแพทย์"
}

sealed interface GoalsIntent {
    data object Save : GoalsIntent

    data object ToggleNotifications : GoalsIntent

    data object OpenGoalMenu : GoalsIntent

    data object DismissGoalMenu : GoalsIntent

    data class SelectGoal(val goal: String) : GoalsIntent

    data class UpdateWeight(val value: String) : GoalsIntent

    data class UpdateHeight(val value: String) : GoalsIntent

    data class UpdateCalorieGoal(val value: String) : GoalsIntent

    data class UpdateProteinGoal(val value: String) : GoalsIntent
}
