package dev.maxmeza.cineapp.ui.screens.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cineapp.composeapp.generated.resources.Res
import cineapp.composeapp.generated.resources.google_logo
import cineapp.composeapp.generated.resources.ic_logo_cine
import dev.maxmeza.cineapp.ui.AppTheme
import dev.maxmeza.cineapp.ui.component.NativeButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onLoginClick: () -> Unit) {
    var showContent by remember { mutableStateOf(true) }
    BoxWithConstraints {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()

        ) {
            var counter by remember { mutableStateOf(0) }

            NativeButton(
                label = "Button Native 2",
                onClick = {
                    counter++
                },
                modifier = Modifier
                    .width(this@BoxWithConstraints.maxWidth.value.dp)
                    .height(this@BoxWithConstraints.maxHeight.value.dp)
            )

            ModalBottomSheet(
                onDismissRequest = { showContent = !showContent },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                scrimColor = Color.Transparent,
                modifier = Modifier
                    .safeDrawingPadding()
                    .fillMaxWidth()
                    .defaultMinSize(minWidth = 300.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    ) {
                    Text("Counter:: $counter", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onLoginClick) {
                        Text("Go Login")
                    }
                }
            }


        }
    }

}

@Composable
@Preview
fun SecondScreenPreview() {
    AppTheme {

//        SecondScreen(onLoginClick = {})
        Surface {
            Box(modifier = Modifier.fillMaxSize().graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }) {
                Avatar(
                    strokeWidth = 10f,
                    modifier = Modifier
                        .offset(1.dp)
                    ) {
                    Image(
                        painterResource(Res.drawable.google_logo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                }

            }
        }
    }

}



@Composable
fun Avatar(
    strokeWidth: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // https://developer.android.com/develop/ui/compose/graphics/draw/shapes
//    val scallopShape = remember {
//        RoundedPolygonShape
//    }
    val stroke = remember(strokeWidth) {
        Stroke(width = strokeWidth)
    }
    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawCircle(
                    Color.Black,
                    size.minDimension / 2,
                    size.center,
                    style = stroke
                )
            }
            .clip(CircleShape)
    ) {
        content()
    }
}