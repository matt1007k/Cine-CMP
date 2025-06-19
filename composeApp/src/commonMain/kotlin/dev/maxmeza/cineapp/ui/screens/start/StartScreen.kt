package dev.maxmeza.cineapp.ui.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cineapp.composeapp.generated.resources.Res
import cineapp.composeapp.generated.resources.facebook_logo
import cineapp.composeapp.generated.resources.google_logo
import cineapp.composeapp.generated.resources.ic_logo_cine
import cineapp.composeapp.generated.resources.imdb_logo
import dev.maxmeza.cineapp.ui.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StartScreen(onSecond: () -> Unit ) {
    val infoWindowInsets = LocalWindowInfo.current.containerSize

    Box(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            bottomBar = {
                Button(
                    onClick = onSecond,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .safeDrawingPadding(),
                ) {
                    Text("Get Started")
                }
            },
            modifier = Modifier
                .width(if (infoWindowInsets.width >= 450) 420.dp else infoWindowInsets.width.dp)
        ) {

            BoxWithConstraints {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),

                    ) {

                    Image(
                        painter = painterResource(Res.drawable.ic_logo_cine),
                        contentDescription = "Corn Pass Logo",
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(Color.LightGray.copy(0.7f)),
                        modifier = Modifier
                            .width(680.dp)
                            .rotate(-25f)
                            .offset(y = (-70).dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .padding(bottom = 100.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                            Text("boxWithConstraintsScope maxWidth::: ${maxWidth}")

                        Image(
                            painter = painterResource(Res.drawable.ic_logo_cine),
                            contentDescription = "Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.width(150.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Your Gateway To The Ultimate Movie Experience",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Discover the latest movies and book your tickets in just a few clicks!",
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                        )


                    }

                }
            }
        }


    }


}


@Composable
@Preview
fun DarkStartScreenPreview() {
    AppTheme {
        StartScreen(onSecond = {})
    }
}