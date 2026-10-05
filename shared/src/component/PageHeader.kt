package com.eb1xnr007.calme.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eb1xnr007.calme.theme.CalMeColors

@Composable
fun PageHeader(
    eyebrow: String,
    title: String,
    subtitle: String,
    eyebrowColor: Color,
    modifier: Modifier = Modifier
) = Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
    Text(eyebrow, color = eyebrowColor, style = MaterialTheme.typography.labelLarge)
    Text(title, style = MaterialTheme.typography.headlineLarge)
    Text(subtitle, color = CalMeColors.Muted, style = MaterialTheme.typography.bodyLarge)
}
