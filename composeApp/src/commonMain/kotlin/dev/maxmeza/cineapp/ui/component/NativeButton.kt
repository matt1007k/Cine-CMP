package dev.maxmeza.cineapp.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun NativeButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)