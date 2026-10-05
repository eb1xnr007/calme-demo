package com.eb1xnr007.calme.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass
import com.eb1xnr007.calme.component.MealRow
import com.eb1xnr007.calme.component.MetricCard
import com.eb1xnr007.calme.component.PageHeader
import com.eb1xnr007.calme.component.ProgressBar
import com.eb1xnr007.calme.resources.Res
import com.eb1xnr007.calme.resources.calorie_settings
import com.eb1xnr007.calme.screen.dashboard.contract.DashboardIntent
import com.eb1xnr007.calme.screen.dashboard.contract.DashboardUiState
import com.eb1xnr007.calme.theme.CalMeColors
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

private const val DAILY_CALORIE_GOAL = 2200
private const val DAILY_PROTEIN_GOAL = 120

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onIntent: (DashboardIntent) -> Unit,
    windowSizeClass: WindowSizeClass,
    onOpenGoals: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    val currentOnIntent by rememberUpdatedState(onIntent)
    LaunchedEffect(Unit) {
        while (true) {
            currentOnIntent(DashboardIntent.RefreshDate)
            delay(60_000.milliseconds)
        }
    }
    val wide = windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
    )
    val expanded = windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
    ) && windowSizeClass.isHeightAtLeastBreakpoint(
        WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND
    )
    val sectionSpacing = if (wide) 24.dp else 18.dp
    LazyColumn(
        state = scrollState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(if (wide) 24.dp else 20.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            PageHeader(
                eyebrow = state.todayLabel,
                title = "สวัสดี, Alex",
                subtitle = "ทุกมื้อเล็ก ๆ คือความคืบหน้า",
                eyebrowColor = CalMeColors.Primary,
                modifier = Modifier.padding(bottom = sectionSpacing)
            )
        }
        item {
            Column(Modifier.fillMaxWidth().padding(bottom = sectionSpacing)) {
                if (expanded) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        CalorieCard(
                            state,
                            onOpenGoals,
                            modifier = Modifier.weight(1.2f),
                            horizontal = true
                        )
                        MetricsRow(state, Modifier.weight(.8f))
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(sectionSpacing)) {
                        CalorieCard(state, onOpenGoals)
                        MetricsRow(state, Modifier.fillMaxWidth())
                    }
                }
            }
        }
        item {
            Row(
                Modifier.fillMaxWidth().padding(bottom = sectionSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "บันทึกวันนี้",
                        color = CalMeColors.Primary,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text("มื้ออาหาร", style = MaterialTheme.typography.titleLarge)
                }
                TextButton(
                    onClick = { onIntent(DashboardIntent.OpenAddMeal) },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("+ เพิ่มมื้อ", color = CalMeColors.Primary, fontWeight = FontWeight.Bold)
                }
            }
        }
        itemsIndexed(state.meals) { index, meal ->
            Column(Modifier.widthIn(max = 760.dp)) {
                MealRow(
                    number = index + 1,
                    name = meal.name,
                    calories = meal.calories,
                    protein = meal.protein,
                    onEdit = { onIntent(DashboardIntent.OpenEditMeal(index)) },
                    onDelete = { onIntent(DashboardIntent.RequestDeleteMeal(index)) }
                )
                if (index < state.meals.lastIndex) HorizontalDivider(color = CalMeColors.Line)
            }
        }
    }
}

@Composable
private fun MetricsRow(state: DashboardUiState, modifier: Modifier = Modifier) = Row(
    modifier = modifier.height(IntrinsicSize.Min),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
) {
    MetricCard(
        modifier = Modifier.weight(1f).fillMaxHeight(),
        badge = "P",
        badgeColor = CalMeColors.ProteinSoft,
        badgeText = CalMeColors.Protein,
        title = "โปรตีน"
    ) {
        Text(
            "${state.consumedProtein}g",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
        Text("/ ${DAILY_PROTEIN_GOAL}g", color = CalMeColors.Muted, fontSize = 12.sp)
        Spacer(Modifier.height(12.dp))
        ProgressBar(
            state.consumedProtein / DAILY_PROTEIN_GOAL.toFloat(),
            CalMeColors.Protein,
            CalMeColors.ProteinSoft
        )
    }
    MetricCard(
        modifier = Modifier.weight(1f).fillMaxHeight(),
        badge = "BMI",
        badgeColor = CalMeColors.PrimarySoft,
        badgeText = CalMeColors.PrimaryDark,
        title = "ดัชนีมวลกาย"
    ) {
        Text("21.9", fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text("สมส่วน", color = CalMeColors.Muted, fontSize = 12.sp)
        Text(
            "ดูคำแนะนำ",
            color = CalMeColors.Primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CalorieCard(
    state: DashboardUiState,
    onOpenGoals: () -> Unit,
    modifier: Modifier = Modifier,
    horizontal: Boolean = false
) = Card(
    modifier = modifier.border(1.dp, CalMeColors.Line, RoundedCornerShape(22.dp)),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = CalMeColors.Surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
) {
    val remaining = (DAILY_CALORIE_GOAL - state.consumedCalories).coerceAtLeast(0)
    val progress = (state.consumedCalories / DAILY_CALORIE_GOAL.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (horizontal) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CalorieSummary(remaining, onOpenGoals, Modifier.weight(1f))
                CalorieRing(state.consumedCalories, progress)
            }
        } else {
            CalorieSummary(remaining, onOpenGoals)
            Spacer(Modifier.height(14.dp))
            CalorieRing(state.consumedCalories, progress)
        }
        Spacer(Modifier.height(10.dp))
        Text(
            "ยอดเยี่ยม เหลืออีก $remaining kcal ก็ถึงเป้าหมาย",
            modifier = Modifier.fillMaxWidth(),
            color = CalMeColors.Muted,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CalorieSummary(remaining: Int, onOpenGoals: () -> Unit, modifier: Modifier = Modifier) =
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(
                "แคลอรี่วันนี้",
                color = CalMeColors.Primary,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    remaining.toString(),
                    modifier = Modifier.alignBy(FirstBaseline),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "kcal เหลือ",
                    modifier = Modifier.alignBy(FirstBaseline),
                    color = CalMeColors.Muted,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Box(
            Modifier.size(40.dp)
                .background(CalMeColors.SurfaceSoft, CircleShape)
                .clickable(role = Role.Button, onClick = onOpenGoals),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.calorie_settings),
                contentDescription = "ตั้งเป้าหมาย",
                tint = CalMeColors.PrimaryDark,
                modifier = Modifier.size(19.dp)
            )
        }
    }

@Composable
private fun CalorieRing(consumed: Int, progress: Float) =
    Box(Modifier.size(184.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = CalMeColors.Primary,
            strokeWidth = 13.dp,
            trackColor = CalMeColors.SurfaceSoft
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                consumed.toString(),
                style = MaterialTheme.typography.displayLarge
            )
            Text("/ $DAILY_CALORIE_GOAL", color = CalMeColors.Muted)
            Text("kcal", color = CalMeColors.Muted)
        }
    }
