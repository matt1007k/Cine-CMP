package dev.maxmeza.cineapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
fun extendedColor(light: Color, dark: Color): Color {
    return if (isSystemInDarkTheme()) dark else light
}

val ColorScheme.textSecondary: Color
    @Composable get() = extendedColor(
        light = Color.Gray,
        dark = Color.LightGray
    )

val ColorScheme.textPrimary: Color
    @Composable get() = extendedColor(
        light = Color.Black,
        dark = Color.White
    )

val Shapes = Shapes(
    extraLarge = RoundedCornerShape(5.dp)
)


@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkTheme) darkScheme else lightScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = Typography
    )
}