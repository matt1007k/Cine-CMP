package dev.maxmeza.cineapp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import dev.maxmeza.cineapp.ui.AppTheme
import dev.maxmeza.cineapp.ui.screens.start.StartScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    AppTheme {
        Surface {
            NavigationRoot()
        }
    }
}

