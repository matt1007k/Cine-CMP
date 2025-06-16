package dev.maxmeza.cineapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import dev.maxmeza.cineapp.LocalNativeViewFactory
import dev.maxmeza.cineapp.ui.extraColor

@Composable
actual fun NativeButton(label: String, onClick: () -> Unit, modifier: Modifier) {
    val factory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = modifier,
        factory = {
            factory.createButtonView(
                label = label,
                onClick = onClick
            )
        }
    )
}