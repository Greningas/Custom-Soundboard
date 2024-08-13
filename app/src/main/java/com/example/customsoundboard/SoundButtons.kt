package com.example.customsoundboard


import androidx.compose.ui.graphics.Color

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth

data class ButtonConfig(val text: String, val soundResId: Int, val color: Color = Color.Blue)

@Composable
fun SoundButton(text: String, soundResId: Int, color: Color, playSound: (Int) -> Unit) {
    Button(
        onClick = { playSound(soundResId) },
        modifier = Modifier.fillMaxWidth(), // Make buttons fill width
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        )
    ) {
        Text(text)
    }
}