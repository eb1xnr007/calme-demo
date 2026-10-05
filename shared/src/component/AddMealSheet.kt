package com.eb1xnr007.calme.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eb1xnr007.calme.theme.CalMeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealSheet(
    name: String,
    calories: String,
    protein: String,
    editing: Boolean,
    canSave: Boolean,
    onNameChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onProteinChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) = ModalBottomSheet(onDismissRequest = onDismiss, containerColor = CalMeColors.Surface) {
    Column(
        Modifier
            .fillMaxWidth()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            if (editing) "แก้ไขรายการอาหาร" else "เพิ่มอาหาร",
            style = MaterialTheme.typography.titleLarge
        )
        Button(
            {},
            Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = CalMeColors.Primary)
        ) { Text("⌁ สแกนบาร์โค้ดเพื่อเติมข้อมูล") }
        Text(
            "CalMe จะค้นหาแคลอรี่และโปรตีนให้อัตโนมัติ",
            color = CalMeColors.Muted,
            style = MaterialTheme.typography.bodyMedium
        )
        CalMeTextField(
            name,
            onNameChange,
            "ชื่ออาหาร",
            Modifier.fillMaxWidth(),
            "เช่น ข้าวกะเพราไก่"
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            CalMeTextField(
                calories,
                onCaloriesChange,
                "แคลอรี่ (kcal)",
                Modifier.weight(1f)
            )
            CalMeTextField(protein, onProteinChange, "โปรตีน (g)", Modifier.weight(1f))
        }
        Button(
            onSave,
            Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = canSave,
            colors = ButtonDefaults.buttonColors(containerColor = CalMeColors.Primary)
        ) { Text(if (editing) "บันทึกการแก้ไข" else "บันทึกมื้อนี้") }
    }
}
