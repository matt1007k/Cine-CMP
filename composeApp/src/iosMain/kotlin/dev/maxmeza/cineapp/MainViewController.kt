package dev.maxmeza.cineapp

import androidx.compose.ui.window.ComposeUIViewController
import dev.maxmeza.cineapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }