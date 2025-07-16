package dev.maxmeza.cineapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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

    Box(modifier = modifier
        .fillMaxSize()
//        .displayCutoutPadding()
        .windowInsetsPadding(WindowInsets.displayCutout)
//        .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        UIKitViewController(
            modifier = modifier
                .fillMaxSize()
//            .fillMaxWidth()
//            .height(300.dp)
,
            factory = {
                factory.createButtonView(
                    label = label, onClick = onClick
                )
            },
        )

    }
}