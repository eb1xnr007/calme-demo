package com.eb1xnr007.calme.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eb1xnr007.calme.theme.CalMeColors

@Composable
fun CalMeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null
) = OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier.heightIn(min = 56.dp),
    label = { Text(label, style = MaterialTheme.typography.bodyMedium) },
    placeholder = placeholder?.let { { Text(it, style = MaterialTheme.typography.bodyLarge) } },
    textStyle = MaterialTheme.typography.bodyLarge.copy(color = CalMeColors.Ink),
    singleLine = true,
    shape = RoundedCornerShape(12.dp),
    colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = CalMeColors.PrimaryDark,
        unfocusedBorderColor = CalMeColors.Line,
        focusedLabelColor = CalMeColors.PrimaryDark,
        cursorColor = CalMeColors.PrimaryDark
    )
)
