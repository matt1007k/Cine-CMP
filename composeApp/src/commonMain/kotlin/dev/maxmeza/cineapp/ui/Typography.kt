package dev.maxmeza.cineapp.ui

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cineapp.composeapp.generated.resources.Res
import cineapp.composeapp.generated.resources.sf_ui_display_black
import cineapp.composeapp.generated.resources.sf_ui_display_bold
import cineapp.composeapp.generated.resources.sf_ui_display_medium
import cineapp.composeapp.generated.resources.sf_ui_display_regular
import dev.maxmeza.cineapp.ui.CustomFont.Companion.sfUiDisplayBlackFont
import org.jetbrains.compose.resources.Font

sealed class CustomFont() {
    companion object {
        val sfUiDisplayBoldFont: Font
            @Composable
            get() = Font(
                Res.font.sf_ui_display_bold,
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            )
        val sfUiDisplayBlackFont: Font
            @Composable
            get() = Font(
                Res.font.sf_ui_display_black,
                weight = FontWeight.Black,
                style = FontStyle.Normal
            )
        val sfUiDisplayRegularFont: Font
            @Composable
            get() = Font(
                Res.font.sf_ui_display_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            )
        val sfUiDisplayMediumFont: Font
            @Composable
            get() = Font(
                Res.font.sf_ui_display_medium,
                weight = FontWeight.Medium,
                style = FontStyle.Normal
            )
    }
}

val SfUiDisplay @Composable get() = FontFamily(
    CustomFont.sfUiDisplayRegularFont,
    CustomFont.sfUiDisplayMediumFont,
    CustomFont.sfUiDisplayBoldFont,
    CustomFont.sfUiDisplayBlackFont,
)

val Typography @Composable get() = Typography(
    headlineMedium = TextStyle(
        fontFamily = SfUiDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = SfUiDisplay,
        fontWeight = FontWeight.Black,
        fontSize = 22.sp
    ),
    displayLarge = TextStyle(
        fontFamily = SfUiDisplay,
        fontWeight = FontWeight.Black
    ),
    bodyLarge = TextStyle(
        fontFamily = SfUiDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
)