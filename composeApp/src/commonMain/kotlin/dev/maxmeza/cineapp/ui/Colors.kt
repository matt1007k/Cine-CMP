package dev.maxmeza.cineapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF2B79EF)

object ColorText {
    val Primary: Color
        @Composable
        get() = if(isSystemInDarkTheme()) Color.White else Color.Black
    val BackgroundPrimary: Color
        @Composable
        get() = if(isSystemInDarkTheme()) Color.Black else Color.White
}