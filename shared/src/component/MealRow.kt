package com.eb1xnr007.calme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.eb1xnr007.calme.resources.Res
import com.eb1xnr007.calme.resources.meal_delete
import com.eb1xnr007.calme.resources.meal_edit
import com.eb1xnr007.calme.theme.CalMeColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun MealRow(
    number: Int,
    name: String,
    calories: Int,
    protein: Int,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val actionWidth = with(LocalDensity.current) { 72.dp.toPx() }
    var swipeOffset by remember(number, name, calories, protein) { mutableFloatStateOf(0f) }
    val actionsVisible = swipeOffset <= -actionWidth / 2f

    Box(modifier.fillMaxWidth()) {
        Row(
            Modifier
                .align(Alignment.CenterEnd)
                .width(72.dp)
                .height(40.dp)
                .zIndex(1f)
                .alpha(if (actionsVisible) 1f else 0f)
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(enabled = actionsVisible, onClick = onEdit),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.meal_edit),
                    contentDescription = "แก้ไข $name",
                    tint = CalMeColors.PrimaryDark,
                    modifier = Modifier.size(16.dp)
                )
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(enabled = actionsVisible, onClick = onDelete),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.meal_delete),
                    contentDescription = "ลบ $name",
                    tint = CalMeColors.Danger,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(CalMeColors.Background)
                .graphicsLayer { translationX = swipeOffset }
                .pointerInput(actionWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            swipeOffset = (swipeOffset + dragAmount)
                                .coerceIn(-actionWidth, 0f)
                        },
                        onDragEnd = {
                            swipeOffset = if (swipeOffset < -actionWidth / 2) {
                                -actionWidth
                            } else {
                                0f
                            }
                        }
                    )
                }
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CalMeColors.SurfaceSoft),
                contentAlignment = Alignment.Center
            ) { Text(number.toString(), color = CalMeColors.PrimaryDark) }
            Spacer(Modifier.width(14.dp))
            Text(
                name,
                Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "$calories kcal",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "โปรตีน ${protein}g",
                    color = CalMeColors.Protein,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
