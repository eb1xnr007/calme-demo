package com.eb1xnr007.calme.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eb1xnr007.calme.resources.Res
import com.eb1xnr007.calme.resources.nav_goals
import com.eb1xnr007.calme.resources.nav_home
import com.eb1xnr007.calme.resources.nav_reports
import com.eb1xnr007.calme.theme.CalMeColors
import org.jetbrains.compose.resources.painterResource

private val navigationPages = listOf(AppPage.Today, AppPage.Reports, AppPage.Goals)

@Composable
fun BottomNavigation(
    currentPage: AppPage,
    onPageChange: (AppPage) -> Unit,
    modifier: Modifier = Modifier
) = Surface(modifier = modifier, color = CalMeColors.Surface, shadowElevation = 8.dp) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        navigationPages.forEach { destination ->
            NavigationItem(
                destination = destination,
                selected = currentPage == destination,
                rail = false,
                onPageChange = onPageChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun WideNavigation(
    currentPage: AppPage,
    onPageChange: (AppPage) -> Unit,
    modifier: Modifier = Modifier
) = Surface(
    modifier = modifier.width(88.dp).fillMaxHeight().padding(vertical = 24.dp),
    color = CalMeColors.Surface,
    shape = RoundedCornerShape(22.dp),
    shadowElevation = 8.dp,
    border = BorderStroke(1.dp, CalMeColors.Line)
) {
    Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.Center) {
        navigationPages.forEach { destination ->
            NavigationItem(
                destination = destination,
                selected = currentPage == destination,
                rail = true,
                onPageChange = onPageChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun NavigationItem(
    destination: AppPage,
    selected: Boolean,
    rail: Boolean,
    onPageChange: (AppPage) -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (selected) CalMeColors.PrimaryDark else CalMeColors.Muted
    val icon = when (destination) {
        AppPage.Today -> Res.drawable.nav_home
        AppPage.Reports -> Res.drawable.nav_reports
        AppPage.Goals -> Res.drawable.nav_goals
    }
    val label = when (destination) {
        AppPage.Today -> "วันนี้"
        AppPage.Reports -> "รายงาน"
        AppPage.Goals -> "เป้าหมาย"
    }
    Box(
        modifier
            .heightIn(min = if (rail) 64.dp else 50.dp)
            .background(
                if (rail && selected) CalMeColors.PrimarySoft else Color.Transparent,
                RoundedCornerShape(14.dp)
            )
            .selectable(selected = selected, role = Role.Tab) { onPageChange(destination) },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(icon), contentDescription = null, tint = color)
            Text(
                label,
                color = color,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp)
            )
        }
    }
}
