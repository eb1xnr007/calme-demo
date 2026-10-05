package com.eb1xnr007.calme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.window.core.layout.WindowSizeClass
import com.eb1xnr007.calme.component.AddMealSheet
import com.eb1xnr007.calme.component.AppPage
import com.eb1xnr007.calme.component.BaseNavDisplay
import com.eb1xnr007.calme.component.BottomNavigation
import com.eb1xnr007.calme.component.BrandHeader
import com.eb1xnr007.calme.component.ConfirmDeleteMealSheet
import com.eb1xnr007.calme.component.WideNavigation
import com.eb1xnr007.calme.di.appModule
import com.eb1xnr007.calme.resources.Res
import com.eb1xnr007.calme.resources.fab_add
import com.eb1xnr007.calme.screen.dashboard.DashboardScreen
import com.eb1xnr007.calme.screen.dashboard.DashboardViewModel
import com.eb1xnr007.calme.screen.dashboard.contract.DashboardIntent
import com.eb1xnr007.calme.screen.goals.GoalsScreen
import com.eb1xnr007.calme.screen.goals.GoalsViewModel
import com.eb1xnr007.calme.screen.reports.ReportsScreen
import com.eb1xnr007.calme.screen.reports.ReportsViewModel
import com.eb1xnr007.calme.theme.CalMeColors
import com.eb1xnr007.calme.theme.CalMeTheme
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.koinConfiguration

@Composable
@DevelopmentEntryPoint
fun App() = KoinApplication(configuration = koinConfiguration { modules(appModule) }) {
    CalMeTheme {
        CalMeApp()
    }
}

@Composable
@Preview
private fun AppPreview() = App()

@Composable
private fun CalMeApp(
    appViewModel: AppViewModel = koinViewModel(),
    dashboardViewModel: DashboardViewModel = koinViewModel(),
    reportsViewModel: ReportsViewModel = koinViewModel(),
    goalsViewModel: GoalsViewModel = koinViewModel()
) {
    val appState = appViewModel.uiState
    val dashboardState = dashboardViewModel.uiState
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val wide = windowSizeClass
        .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
    val todayScrollState = rememberLazyListState()
    val hideThreshold = with(LocalDensity.current) { 60.dp.roundToPx() }
    val hiddenOffset = with(LocalDensity.current) { 88.dp.roundToPx() }
    var isAddButtonHidden by remember { mutableStateOf(false) }
    val backStack = remember { mutableStateListOf(appState.page) }
    LaunchedEffect(appState.page) {
        if (backStack.lastOrNull() != appState.page) {
            backStack[backStack.lastIndex] = appState.page
        }
        if (appState.page == AppPage.Today) {
            isAddButtonHidden = todayScrollState.firstVisibleItemIndex > 0 ||
                todayScrollState.firstVisibleItemScrollOffset > hideThreshold
        }
    }
    LaunchedEffect(todayScrollState, hideThreshold) {
        var lastIndex = todayScrollState.firstVisibleItemIndex
        var lastOffset = todayScrollState.firstVisibleItemScrollOffset
        snapshotFlow {
            todayScrollState.firstVisibleItemIndex to todayScrollState.firstVisibleItemScrollOffset
        }.collect { (index, offset) ->
            if (index != lastIndex || offset != lastOffset) {
                val scrollingDown = index > lastIndex || (index == lastIndex && offset > lastOffset)
                isAddButtonHidden = scrollingDown && (index > 0 || offset > hideThreshold)
                lastIndex = index
                lastOffset = offset
            }
        }
    }
    val destinations = entryProvider {
        entry<AppPage.Today> {
            DashboardScreen(
                dashboardState,
                dashboardViewModel::onIntent,
                windowSizeClass = windowSizeClass,
                onOpenGoals = {
                    appViewModel.onIntent(AppIntent.SelectPage(AppPage.Goals))
                },
                scrollState = todayScrollState
            )
        }

        entry<AppPage.Reports> {
            ReportsScreen(reportsViewModel.uiState, reportsViewModel::onIntent, windowSizeClass)
        }

        entry<AppPage.Goals> {
            GoalsScreen(goalsViewModel.uiState, goalsViewModel::onIntent, windowSizeClass)
        }
    }
    Scaffold(
        containerColor = CalMeColors.Background,
        topBar = {
            if (!wide) {
                BrandHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
                )
            }
        },
        bottomBar = {
            if (!wide) {
                BottomNavigation(
                    appState.page,
                    onPageChange = { appViewModel.onIntent(AppIntent.SelectPage(it)) }
                )
            }
        },
        floatingActionButton = {
            if (!wide && appState.page == AppPage.Today) {
                AnimatedVisibility(
                    visible = !isAddButtonHidden,
                    enter = fadeIn(tween(200)) + slideInVertically(tween(200)) { hiddenOffset },
                    exit = fadeOut(tween(200)) + slideOutVertically(tween(200)) { hiddenOffset }
                ) {
                    FloatingActionButton(
                        onClick = {
                            if (!isAddButtonHidden) {
                                dashboardViewModel.onIntent(DashboardIntent.OpenAddMeal)
                            }
                        },
                        modifier = Modifier.padding(end = 6.dp).size(56.dp),
                        shape = CircleShape,
                        containerColor = CalMeColors.Primary,
                        contentColor = Color.White,
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 12.dp)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.fab_add),
                            contentDescription = "เพิ่มอาหาร",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        if (wide) {
            Box(
                Modifier.fillMaxSize().padding(contentPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    Modifier.fillMaxHeight()
                        .widthIn(max = 1180.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    WideNavigation(
                        appState.page,
                        onPageChange = { appViewModel.onIntent(AppIntent.SelectPage(it)) }
                    )
                    Column(Modifier.weight(1f).fillMaxHeight()) {
                        BrandHeader(
                            Modifier.fillMaxWidth().padding(top = 32.dp, bottom = 16.dp)
                        )
                        BaseNavDisplay(
                            backStack = backStack,
                            modifier = Modifier.weight(1f),
                            entryProvider = destinations
                        )
                    }
                }
            }
        } else {
            BaseNavDisplay(
                backStack = backStack,
                modifier = Modifier.padding(contentPadding),
                entryProvider = destinations
            )
        }
    }
    if (dashboardState.isAddMealVisible) {
        AddMealSheet(
            name = dashboardState.mealName,
            calories = dashboardState.caloriesInput,
            protein = dashboardState.proteinInput,
            editing = dashboardState.editingMealIndex != null,
            canSave = dashboardState.canSaveMeal,
            onNameChange = { dashboardViewModel.onIntent(DashboardIntent.UpdateMealName(it)) },
            onCaloriesChange = { dashboardViewModel.onIntent(DashboardIntent.UpdateCalories(it)) },
            onProteinChange = { dashboardViewModel.onIntent(DashboardIntent.UpdateProtein(it)) },
            onSave = { dashboardViewModel.onIntent(DashboardIntent.SaveMeal) },
            onDismiss = { dashboardViewModel.onIntent(DashboardIntent.DismissAddMeal) }
        )
    }
    dashboardState.deletingMeal?.let { meal ->
        ConfirmDeleteMealSheet(
            name = meal.name,
            onConfirm = { dashboardViewModel.onIntent(DashboardIntent.ConfirmDeleteMeal) },
            onDismiss = { dashboardViewModel.onIntent(DashboardIntent.CancelDeleteMeal) }
        )
    }
}
