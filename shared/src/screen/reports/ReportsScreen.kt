package com.eb1xnr007.calme.screen.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass
import com.eb1xnr007.calme.component.PageContent
import com.eb1xnr007.calme.component.PageHeader
import com.eb1xnr007.calme.screen.reports.contract.ReportPeriod
import com.eb1xnr007.calme.screen.reports.contract.ReportsIntent
import com.eb1xnr007.calme.screen.reports.contract.ReportsUiState
import com.eb1xnr007.calme.theme.CalMeColors

@Composable
fun ReportsScreen(
    state: ReportsUiState,
    onIntent: (ReportsIntent) -> Unit,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) = PageContent(windowSizeClass, modifier = modifier) {
    PageHeader(
        eyebrow = "ภาพรวม",
        title = "รายงานสุขภาพ",
        subtitle = "เห็นแนวโน้ม แล้วปรับได้ทันที",
        eyebrowColor = CalMeColors.Primary
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CalMeColors.SurfaceSoft)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ReportPeriod.entries.forEach { period ->
            val selected = state.period == period
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .shadow(if (selected) 2.dp else 0.dp, RoundedCornerShape(10.dp))
                    .background(
                        if (selected) Color.White else Color.Transparent,
                        RoundedCornerShape(10.dp)
                    )
                    .selectable(selected = selected, role = Role.Tab) {
                        onIntent(ReportsIntent.SelectPeriod(period))
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    period.label,
                    color = if (selected) CalMeColors.Ink else CalMeColors.Muted,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    if (
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) &&
        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(Modifier.weight(1.25f)) { ReportSummaryCard(state) }
            Column(
                Modifier.weight(.75f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ReportInsightCard()
                ReportStatsCard(state)
            }
        }
    } else {
        ReportSummaryCard(state)
        ReportInsightCard()
        ReportStatsCard(state)
    }
}

@Composable
private fun ReportSummaryCard(state: ReportsUiState) = ReportCard {
    Text(
        when (state.period) {
            ReportPeriod.Day -> "สรุปวันนี้"
            ReportPeriod.Week -> "ค่าเฉลี่ย 7 วัน"
            ReportPeriod.Month -> "ค่าเฉลี่ย 30 วัน"
        },
        color = CalMeColors.Primary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.72.sp
    )
    Spacer(Modifier.height(8.dp))
    Text("1,376 kcal", fontSize = 34.sp, fontWeight = FontWeight.Bold)
    Text("เป้าหมาย 2,200 kcal ต่อวัน", color = CalMeColors.Muted, fontSize = 13.sp)
    val bars = when (state.period) {
        ReportPeriod.Day -> listOf(42, 68, 63)
        ReportPeriod.Week -> listOf(70, 78, 61, 83, 74, 92, 79)
        ReportPeriod.Month -> listOf(60, 77, 71, 88, 74, 82, 79, 93, 76, 84)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(144.dp)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        bars.forEachIndexed { index, height ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height((124f * height / 100f).dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 3.dp,
                            bottomEnd = 3.dp
                        )
                    )
                    .background(
                        if (index ==
                            bars.lastIndex
                        ) {
                            CalMeColors.Primary
                        } else {
                            CalMeColors.PrimarySoft
                        }
                    )
            )
        }
    }
}

@Composable
private fun ReportInsightCard() = ReportCard {
    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(CalMeColors.WarningSoft, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("✦", color = CalMeColors.Warning)
        }
        Column {
            Text("คุณกำลังเดินตามเป้าหมาย", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(
                "โปรตีนและแคลอรี่ของคุณอยู่ในทิศทางที่ดี",
                color = CalMeColors.Muted,
                fontSize = 13.sp,
                lineHeight = 19.5.sp
            )
        }
    }
}

@Composable
private fun ReportStatsCard(state: ReportsUiState) = ReportCard {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(Modifier.weight(1f)) {
            Text("โปรตีนเฉลี่ย", color = CalMeColors.Muted, fontSize = 12.sp)
            Spacer(Modifier.height(4.dp))
            Text("81g", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        }
        Box(
            Modifier
                .width(1.dp)
                .height(46.dp)
                .background(CalMeColors.Line)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text("วันตามเป้า", color = CalMeColors.Muted, fontSize = 12.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                when (state.period) {
                    ReportPeriod.Day -> "1/1"
                    ReportPeriod.Week -> "6/7"
                    ReportPeriod.Month -> "29/30"
                },
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ReportCard(content: @Composable ColumnScope.() -> Unit) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, CalMeColors.Line, RoundedCornerShape(22.dp)),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = CalMeColors.Surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
) {
    Column(Modifier.fillMaxWidth().padding(20.dp), content = content)
}
