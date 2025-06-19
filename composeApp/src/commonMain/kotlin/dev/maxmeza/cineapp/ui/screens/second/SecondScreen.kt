package dev.maxmeza.cineapp.ui.screens.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cineapp.composeapp.generated.resources.Res
import cineapp.composeapp.generated.resources.ic_logo_cine
import cineapp.composeapp.generated.resources.start2
import dev.maxmeza.cineapp.ui.AppTheme
import dev.maxmeza.cineapp.ui.ColorText
import dev.maxmeza.cineapp.ui.ColorText.BackgroundPrimary
import dev.maxmeza.cineapp.ui.screens.login.paddingContainer
import dev.maxmeza.cineapp.ui.textPrimary
import dev.maxmeza.cineapp.ui.textSecondary
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(onLoginClick: () -> Unit) {
    val backgroundPrimaryLocal = ColorText.BackgroundPrimary
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundPrimaryLocal),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(Res.drawable.start2),
            contentScale = ContentScale.Crop,
            contentDescription = "Bg start 2",
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                               0.0f to Color.Transparent,
                               0.2f to backgroundPrimaryLocal.copy(0.15f),
                               0.7f to backgroundPrimaryLocal.copy(0.85f),
                                1f to backgroundPrimaryLocal
                            )
                        )
                    )
                }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_logo_cine),
                contentScale = ContentScale.Crop,
                contentDescription = "Logo Corn Pass",
                modifier = Modifier
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Experience Cinema Like\n  Never Before!",
                color = MaterialTheme.colorScheme.textPrimary,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Find the latest releases, explore top-rated films,\n and secure your seat today.",
                color = MaterialTheme.colorScheme.textSecondary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .paddingContainer()
            ) {
                Text("Let's Go!")
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
@Preview
fun SecondScreenPreview() {
    AppTheme {
        SecondScreen(onLoginClick = {})
    }

}
