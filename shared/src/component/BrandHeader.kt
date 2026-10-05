package com.eb1xnr007.calme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eb1xnr007.calme.resources.Res
import com.eb1xnr007.calme.resources.brand_logo
import com.eb1xnr007.calme.theme.CalMeColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun BrandHeader(modifier: Modifier = Modifier) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
) {
    Box(
        Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(CalMeColors.Primary),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.brand_logo),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(21.dp)
        )
    }
    Spacer(Modifier.width(10.dp))
    Text(
        "CalMe-Demo",
        style = MaterialTheme.typography.titleLarge
    )
    Spacer(Modifier.weight(1f))
    Box(
        Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(CalMeColors.PrimaryDark),
        contentAlignment = Alignment.Center
    ) { Text("A", color = Color.White, style = MaterialTheme.typography.labelLarge) }
}
