package dev.maxmeza.cineapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.maxmeza.cineapp.di.initKoin
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CineApp",

    ) {
        window.minimumSize = Dimension(800, 900)
        initKoin()
        App()
    }
}