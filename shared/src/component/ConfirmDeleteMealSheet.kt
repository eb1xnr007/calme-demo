package com.eb1xnr007.calme.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eb1xnr007.calme.theme.CalMeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteMealSheet(name: String, onConfirm: () -> Unit, onDismiss: () -> Unit) =
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = CalMeColors.Surface) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 22.dp)
        ) {
            Text(
                "ลบรายการอาหาร?",
                color = CalMeColors.Ink,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                buildAnnotatedString {
                    append("รายการ ")
                    withStyle(SpanStyle(color = CalMeColors.Ink, fontWeight = FontWeight.Bold)) {
                        append(name)
                    }
                    append(" จะถูกลบออกจากวันนี้")
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 20.dp),
                color = CalMeColors.Muted,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, CalMeColors.Line),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CalMeColors.Ink)
                ) { Text("ยกเลิก", fontWeight = FontWeight.Bold) }
                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CalMeColors.Danger)
                ) { Text("ลบรายการ", fontWeight = FontWeight.Bold) }
            }
        }
    }
