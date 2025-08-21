package dev.maxmeza.cineapp.ui.screens.graphic

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.ktor.websocket.Frame
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

@Composable
fun GraphDonutsScreen(onSecond: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                // This is the DrawScope where you can draw behind the content
                val arcColor = Color.Red
                val arcSize = Size(200.dp.toPx(), 200.dp.toPx())
                val topLeftOffset = Offset(
                    x = (size.width - arcSize.width) / 2,
                    y = (size.height - arcSize.height) / 2
                )
                drawArc(
                    color = arcColor,
                    startAngle = 0f,
                    sweepAngle = 270f, // Draw a 270-degree arc
                    useCenter = true, // Set to true to draw a pie slice
                    topLeft = topLeftOffset,
                    size = arcSize,
                    style = Stroke(width = 5.dp.toPx()) // Draw as a stroke
                )
            }
            .background(Color.LightGray) // Background for visibility
            .clickable { onSecond() }
    ) {
        // Content of the Box, drawn on top of the arc
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue)
        )
    }
}

@Preview
@Composable
fun GraphDonutsScreenPreview() {
    FocusScreen()

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusScreen() {
    var progress by remember { mutableStateOf(0.0f) }
    var timeLeft by remember { mutableStateOf(25.minutes.inWholeMilliseconds) } // TimeUnit.MINUTES.toMillis(25) => 25 minutes in millis
    val totalDurationMillis = 25.minutes.inWholeMilliseconds // TimeUnit.MINUTES.toMillis(25) => Max duration for progress calculation

    // Simulate timer countdown
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000)
            timeLeft -= 1000
            progress = 1.0f - (timeLeft.toFloat() / totalDurationMillis)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Focus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                actions = {
                    IconButton(onClick = { /* TODO: Handle notification click */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Timer display with circular progress bar
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(250.dp)
            ) {
                CircularProgressBar(
                    progress = progress,
                    size = 250.dp,
                    strokeWidth = 12.dp,
                    color = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Hours", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Min", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Sec", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        val hours = timeLeft.hours.inWholeHours % 24
                        val minutes = timeLeft.minutes.inWholeMinutes % 60
                        val seconds = timeLeft.seconds.inWholeSeconds % 60
                        Text(
                            text = "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}",
                            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 48.sp // Adjust font size for timer
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            // Start Session Button
            Button(
                onClick = { /* TODO: Start session action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Start Session", color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Options List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(vertical = 8.dp)
            ) {
                CustomListItem(
                    title = "Select Duration",
                    onClick = { /* TODO: Handle select duration */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                CustomListItem(
                    title = "Apps Blocked",
                    onClick = { /* TODO: Handle apps blocked */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                CustomListItem(
//                    icon = Icons.Default.Star, // Placeholder icon
                    title = "Difficulty",
                    onClick = { /* TODO: Handle difficulty */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                WarningListItem(
                    text = "There will be increasing delays before you can Snooze again!",
                    onClick = { /* TODO: Handle warning tap */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                CustomListItem(
                    title = "Schedule for later",
                    onClick = { /* TODO: Handle schedule */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CircularProgressBar(
    progress: Float, // 0.0f to 1.0f
    size: Dp = 150.dp,
    strokeWidth: Dp = 10.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    content: @Composable () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000), label = "progressAnimation"
    )

//    Canvas(modifier = Modifier.size(size)) {
//        // Background circle
//        drawArc(
//            color = backgroundColor,
//            startAngle = 0f,
//            sweepAngle = 360f,
//            useCenter = false,
//            topLeft = Offset(strokeWidth.toPx() / 2, strokeWidth.toPx() / 2),
//            size = Size(size.toPx() - strokeWidth.toPx(), size.toPx() - strokeWidth.toPx()),
//            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
//        )
//
//        // Progress arc
//        drawArc(
//            color = color,
//            startAngle = -90f, // Start from top
//            sweepAngle = animatedProgress * 360f,
//            useCenter = false,
//            topLeft = Offset(strokeWidth.toPx() / 2, strokeWidth.toPx() / 2),
//            size = Size(size.toPx() - strokeWidth.toPx(), size.toPx() - strokeWidth.toPx()),
//            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
//        )
//    }
    Box(
        modifier = Modifier
            .size(size)
            .drawBehind {
                val strokeWidthPx = strokeWidth.toPx()
                val inset = strokeWidthPx / 2
                val diameter = size.toPx() - strokeWidthPx

                // Background circle
                drawArc(
                    color = backgroundColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(inset, inset),
                    size = Size(diameter, diameter),
                    style = Stroke(strokeWidthPx, cap = StrokeCap.Round)
                )

                // Progress arc
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = animatedProgress * 360f,
                    useCenter = false,
                    topLeft = Offset(inset, inset),
                    size = Size(diameter, diameter),
                    style = Stroke(strokeWidthPx, cap = StrokeCap.Round)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        content() // The content you want to display inside the progress bar
    }
}

@Composable
fun CustomListItem(
    icon: @Composable () -> Unit = { Icon(Icons.Default.Star, contentDescription = null) },
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            icon()
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
        }
        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Go", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun WarningListItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Info, contentDescription = "Info", tint = MaterialTheme.colorScheme.tertiary)
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Go", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                actions = {
                    IconButton(onClick = { /* TODO: Handle more options */ }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // User Info Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Daniel George",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "hellodaniel@gmail.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                AsyncImage(
                    model = "https://placehold.co/60x60/888888/FFFFFF?text=DG", // Placeholder image
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Goals and Progress Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = "Daily Goals",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Daily Goals",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "8 Goals",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.TrendingUp,
                                contentDescription = "Progress",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Progress",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "3/8 Goals",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Premium Features Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), // Using primaryContainer for a light blue
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Premium Features",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Take your productivity to the next level with exclusive tools!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "$9.99/month",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Limited Offer",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.tertiary // Orange accent
                            )
                        }
                        Button(
                            onClick = { /* TODO: Handle join now */ },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Join Now", color = MaterialTheme.colorScheme.onPrimary)
                            Spacer(Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "Join", modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Settings List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(vertical = 8.dp)
            ) {
                ProfileListItem(
                    icon = Icons.Default.AccountCircle,
                    title = "Account",
                    onClick = { /* TODO: Handle account */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ProfileListItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    onClick = { /* TODO: Handle notifications */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ProfileListItem(
                    icon = Icons.Default.Settings,
                    title = "General",
                    onClick = { /* TODO: Handle general settings */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ProfileListItem(
                    icon = Icons.Default.CalendarMonth,
                    title = "Calendar",
                    onClick = { /* TODO: Handle calendar settings */ }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ProfileListItem(
                    icon = Icons.Default.Palette,
                    title = "Theme",
                    onClick = { /* TODO: Handle theme settings */ }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProfileListItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
        }
        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Go", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        BottomNavItem(Icons.Default.Home, "Home", "home"),
        BottomNavItem(Icons.Default.CalendarMonth, "Calendar", "calendar"),
        BottomNavItem(Icons.Default.Psychology, "Focus", "focus"), // Using psychology for focus icon
        BottomNavItem(Icons.Default.AccountCircle, "Profile", "profile")
    )
    var selectedItem by remember { mutableIntStateOf(3) } // Highlight profile for preview

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant, // Using surfaceVariant for the bar background
        tonalElevation = 0.dp // No shadow
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = { selectedItem = index /* TODO: Handle navigation */ },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant // Indicator matches bar color for subtle effect
                )
            )
        }
    }
}