package com.eb1xnr007.calme.screen.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass
import com.eb1xnr007.calme.component.PageContent
import com.eb1xnr007.calme.component.PageHeader
import com.eb1xnr007.calme.resources.Res
import com.eb1xnr007.calme.resources.goal_chevron
import com.eb1xnr007.calme.screen.goals.contract.GoalsIntent
import com.eb1xnr007.calme.screen.goals.contract.GoalsUiState
import com.eb1xnr007.calme.theme.CalMeColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoalsScreen(
    state: GoalsUiState,
    onIntent: (GoalsIntent) -> Unit,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) = PageContent(windowSizeClass, modifier = modifier) {
    GoalsHeader()
    if (
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) &&
        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(Modifier.weight(1.2f)) {
                GoalFormCard(state, onIntent, sideBySide = true)
            }
            Column(
                Modifier.weight(.8f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BmiCard(state)
                NotificationCard(state, onIntent)
            }
        }
    } else {
        GoalFormCard(
            state,
            onIntent,
            sideBySide = windowSizeClass.isWidthAtLeastBreakpoint(
                WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
            )
        )
        BmiCard(state)
        NotificationCard(state, onIntent)
    }
}

@Composable
private fun GoalsHeader() = PageHeader(
    eyebrow = "เป้าหมายของคุณ",
    title = "โปรไฟล์ & BMI",
    subtitle = "ใช้เพื่อคำนวณเป้าหมายให้เหมาะกับคุณ",
    eyebrowColor = CalMeColors.Primary
)

@Composable
private fun GoalFormCard(
    state: GoalsUiState,
    onIntent: (GoalsIntent) -> Unit,
    sideBySide: Boolean
) = GoalCard {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        if (sideBySide) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                GoalTextField(
                    "น้ำหนัก (kg)",
                    state.weight,
                    KeyboardType.Decimal,
                    onValueChange = { onIntent(GoalsIntent.UpdateWeight(it)) },
                    modifier = Modifier.weight(1f)
                )
                GoalTextField(
                    "ส่วนสูง (cm)",
                    state.height,
                    KeyboardType.Number,
                    onValueChange = { onIntent(GoalsIntent.UpdateHeight(it)) },
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            GoalTextField(
                "น้ำหนัก (kg)",
                state.weight,
                KeyboardType.Decimal,
                onValueChange = { onIntent(GoalsIntent.UpdateWeight(it)) }
            )
            GoalTextField(
                "ส่วนสูง (cm)",
                state.height,
                KeyboardType.Number,
                onValueChange = { onIntent(GoalsIntent.UpdateHeight(it)) }
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                "เป้าหมายหลัก",
                color = CalMeColors.Muted,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Box {
                var menuWidth by remember { mutableIntStateOf(0) }
                val menuWidthDp = with(LocalDensity.current) { menuWidth.toDp() }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onSizeChanged { menuWidth = it.width }
                        .height(48.dp)
                        .border(1.dp, CalMeColors.Line, RoundedCornerShape(12.dp))
                        .clickable(role = Role.Button) { onIntent(GoalsIntent.OpenGoalMenu) }
                        .padding(start = 12.dp, end = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(state.goal, color = CalMeColors.Ink, fontWeight = FontWeight.Bold)
                    Icon(
                        painter = painterResource(Res.drawable.goal_chevron),
                        contentDescription = null,
                        tint = CalMeColors.Ink,
                        modifier = Modifier.size(
                            18.dp
                        ).rotate(if (state.isGoalMenuVisible) 180f else 0f)
                    )
                }
                DropdownMenu(
                    expanded = state.isGoalMenuVisible,
                    onDismissRequest = { onIntent(GoalsIntent.DismissGoalMenu) },
                    modifier = Modifier.width(menuWidthDp),
                    offset = DpOffset(0.dp, 8.dp),
                    containerColor = CalMeColors.Surface,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    listOf("คงน้ำหนัก", "ลดน้ำหนัก", "เพิ่มกล้ามเนื้อ").forEach { goal ->
                        val selected = state.goal == goal
                        DropdownMenuItem(
                            text = {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(goal)
                                    if (selected) Text("✓", fontWeight = FontWeight.Bold)
                                }
                            },
                            onClick = { onIntent(GoalsIntent.SelectGoal(goal)) },
                            modifier = Modifier
                                .height(44.dp)
                                .background(
                                    if (selected) {
                                        CalMeColors.PrimarySoft
                                    } else {
                                        Color.Transparent
                                    },
                                    RoundedCornerShape(9.dp)
                                )
                        )
                    }
                }
            }
        }
        GoalTextField(
            "เป้าหมายแคลอรี่ / วัน",
            state.calorieGoal,
            KeyboardType.Number,
            onValueChange = { onIntent(GoalsIntent.UpdateCalorieGoal(it)) }
        )
        GoalTextField(
            "เป้าหมายโปรตีน / วัน (g)",
            state.proteinGoal,
            KeyboardType.Number,
            onValueChange = { onIntent(GoalsIntent.UpdateProteinGoal(it)) }
        )
        Button(
            onClick = { onIntent(GoalsIntent.Save) },
            enabled = state.canSave,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CalMeColors.Primary),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("บันทึก & ประเมินใหม่", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun BmiCard(state: GoalsUiState) = GoalCard {
    Text(
        "BMI ${state.bmiValue} · ${state.bmiLabel}",
        color = CalMeColors.Primary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(4.dp))
    Text(
        state.bmiTitle,
        color = CalMeColors.Ink,
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(6.dp))
    Text(state.bmiCopy, color = CalMeColors.Muted, fontSize = 14.sp, lineHeight = 21.sp)
}

@Composable
private fun NotificationCard(state: GoalsUiState, onIntent: (GoalsIntent) -> Unit) = GoalCard {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                "แจ้งเตือนเป้าหมาย",
                color = CalMeColors.Ink,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(5.dp))
            Text(
                "เตือนเมื่อแคลอรี่ยังไม่ถึง หรือเกินเป้าหมาย",
                color = CalMeColors.Muted,
                fontSize = 12.sp,
                lineHeight = 17.4.sp
            )
        }
        Box(
            modifier = Modifier
                .size(width = 48.dp, height = 28.dp)
                .background(
                    if (state.notificationsEnabled) CalMeColors.Primary else CalMeColors.Line,
                    CircleShape
                )
                .toggleable(
                    value = state.notificationsEnabled,
                    role = Role.Switch,
                    onValueChange = { onIntent(GoalsIntent.ToggleNotifications) }
                )
                .semantics { contentDescription = "แจ้งเตือนเป้าหมาย" }
                .padding(3.dp),
            contentAlignment = if (state.notificationsEnabled) {
                Alignment.CenterEnd
            } else {
                Alignment.CenterStart
            }
        ) {
            Box(Modifier.size(22.dp).background(Color.White, CircleShape))
        }
    }
}

@Composable
private fun GoalCard(content: @Composable ColumnScope.() -> Unit) = Card(
    modifier = Modifier.fillMaxWidth().border(1.dp, CalMeColors.Line, RoundedCornerShape(22.dp)),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = CalMeColors.Surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
) {
    Column(Modifier.fillMaxWidth().padding(20.dp), content = content)
}

@Composable
private fun GoalTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) = Column(modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Text(label, color = CalMeColors.Muted, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, CalMeColors.Line, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp)
            .semantics { contentDescription = label },
        textStyle = TextStyle(color = CalMeColors.Ink, fontSize = 16.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        cursorBrush = SolidColor(CalMeColors.PrimaryDark),
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) { innerTextField() }
        }
    )
}
