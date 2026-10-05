package com.eb1xnr007.calme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(progress: Float, color: Color, track: Color, modifier: Modifier = Modifier) = Box(
    modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(CircleShape)
        .background(track)
) {
    Box(
        Modifier
            .fillMaxWidth(progress.coerceIn(0f, 1f))
            .height(6.dp)
            .clip(CircleShape)
            .background(color)
    )
}
