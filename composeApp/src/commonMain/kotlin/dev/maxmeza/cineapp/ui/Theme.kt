package dev.maxmeza.cineapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val lightScheme = lightColorScheme(
    primary = Blue,
    onPrimary = Color(0xFFFFFFFF),
)

val darkScheme = darkColorScheme(
    primary = Blue,
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0XFFFFFFFF)
)


@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) darkScheme else lightScheme

    val typography = Typography(
        headlineSmall = TextStyle(
            fontFamily = FontFamily(CustomFont.sfUiDisplayBlackFont),
            fontWeight = FontWeight.Black,
            fontSize = 22.sp
        ),
        displayLarge = TextStyle(
            fontFamily = FontFamily(CustomFont.sfUiDisplayBlackFont),
            fontWeight = FontWeight.Black
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily(CustomFont.sfUiDisplayMediumFont),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = typography
    )
}