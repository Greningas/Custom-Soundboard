package com.example.customsoundboard


import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.graphics.Color

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ButtonConfig(val text: String, val soundResId: Int, val color: Color = Color.Blue)

@Composable
fun SoundButton(text: String, soundResId: Int, color: Color, playSound: (Int) -> Unit) {
    Button(
        onClick = { playSound(soundResId) }, // Call playSound with the resource ID
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}