package com.eb1xnr007.calme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eb1xnr007.calme.theme.CalMeColors

@Composable
fun MetricCard(
    badge: String,
    badgeColor: Color,
    badgeText: Color,
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) = Card(
    modifier = modifier.border(1.dp, CalMeColors.Line, RoundedCornerShape(22.dp)),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = CalMeColors.Surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
) {
    Column(Modifier.padding(20.dp)) {
        Box(
            Modifier
                .size(32.dp)
                .background(badgeColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                badge,
                color = badgeText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(title, color = CalMeColors.Muted, fontSize = 13.sp)
        Spacer(Modifier.height(2.dp))
        content()
    }
}
