package dev.maxmeza.cineapp.ui.component

import androidx.compose.material3.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun NativeButton(label: String, onClick: () -> Unit, modifier: Modifier) {
    Button(onClick, modifier) {
        Text(label)
    }
}