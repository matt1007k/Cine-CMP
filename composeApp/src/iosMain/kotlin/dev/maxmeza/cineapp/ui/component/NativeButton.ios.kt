package dev.maxmeza.cineapp.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import dev.maxmeza.cineapp.LocalNativeViewFactory
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun NativeButton(label: String, onClick: () -> Unit, modifier: Modifier) {
    val factory = LocalNativeViewFactory.current
    val screenSize = UIScreen.mainScreen.bounds.useContents { size }

    UIKitViewController(
        modifier = modifier,
        factory = {
            factory.createButtonView(
                label = label, onClick = onClick
            )
        }
    )
}