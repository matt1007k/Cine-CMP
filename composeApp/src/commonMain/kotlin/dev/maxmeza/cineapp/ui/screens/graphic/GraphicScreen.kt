package dev.maxmeza.cineapp.ui.screens.graphic

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.maxmeza.cineapp.ui.AppTheme
import dev.maxmeza.cineapp.ui.screens.sleep.SleepGraphData
import dev.maxmeza.cineapp.util.AppLogger
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GraphicScreen() {
//    val viewModel: JetLaggedHomeScreenViewModel = viewModel()
//    val uiState =
//        viewModel.uiState.collectAsStateWithLifecycle()
//    val scope = rememberCoroutineScope()
//    LaunchedEffect(Unit) {
//        scope.launch {
//            AppLogger.e("LaunchedEffect", uiState.value.sleepGraphData.toString())
//        }
//    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

//        FlowRow(
//            modifier = Modifier
//                .fillMaxSize()
//                .windowInsetsPadding(WindowInsets.statusBars),
//            horizontalArrangement = Arrangement.Center,
//            verticalArrangement = Arrangement.Center,
//            maxItemsInEachRow = 3
//        ) {
//            JetLaggedSleepGraphCard(uiState.value.sleepGraphData, Modifier.widthIn(max = 600.dp))
//        }
        InfiniteAnimation()
    }
}

@Preview
@Composable
fun GraphicScreenPreview() {
    AppTheme {
        Surface { GraphicScreen() }
    }
}

@Composable
fun InfiniteAnimation(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(
        0.95f, 1.04f, infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )
    Text("👋 Infinite Animation!", fontSize = 24.sp, modifier = modifier.scale(scale))
}

@Composable
fun SleepBarLocal(
    sleepData: SleepGraphData,
    modifier: Modifier = Modifier
) {
    val textMeasure = rememberTextMeasurer()
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val transition = updateTransition(targetState = isExpanded)
    val height by transition.animateDp { targetExpanded ->
        if(targetExpanded) 110.dp else 24.dp
    }

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val brush = Brush.verticalGradient(listOf(Color.Yellow, Color.Green))
                val textResult = textMeasure.measure(AnnotatedString("😍"))
                onDrawBehind {
                    drawRoundRect(brush, cornerRadius = CornerRadius(10.dp.toPx()))
                    drawText(textResult)
                }
            }
            .fillMaxWidth()
            .height(height)
            .clickable {
                isExpanded = !isExpanded
            }
    )
}


@Composable
fun HomeScreenCardHeading(text: String) {
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun BasicInformationalCard(
    modifier: Modifier = Modifier,
    borderColor: Color,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
            .padding(8.dp),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Box {
            content()
        }
    }
}

@Composable
fun JetLaggedSleepGraphCard(
    sleepState: SleepGraphData,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(SleepTab.Week) }

    BasicInformationalCard(
        borderColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Column {
            HomeScreenCardHeading(text = "Sleep")
            JetLaggedHeaderTabs(
                onTabSelected = { selectedTab = it },
                selectedTab = selectedTab,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            JetLaggedTimeGraph(
                sleepState
            )
        }
    }
}

@Composable
private fun JetLaggedTimeGraph(
    sleepGraphData: SleepGraphData,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val hours = (sleepGraphData.earliestStartHour..23) + (0..sleepGraphData.latestEndHour)

    TimeGraph(
        modifier = modifier
            .horizontalScroll(scrollState)
            .wrapContentSize(),
        dayItemsCount = sleepGraphData.sleepDayData.size,
        hoursHeader = {
            HoursHeader(hours)
        },
        dayLabel = { index ->
            val data = sleepGraphData.sleepDayData[index]
            DayLabel(data.startDate.dayOfWeek)
        },
        bar = { index ->
            val data = sleepGraphData.sleepDayData[index]
            // We have access to Modifier.timeGraphBar() as we are now in TimeGraphScope
            SleepBar(
                sleepData = data,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .timeGraphBar(
                        start = data.firstSleepStart,
                        end = data.lastSleepEnd,
                        hours = hours,
                    )
            )
        }
    )
}

@Composable
private fun DayLabel(dayOfWeek: DayOfWeek) {
    Text(
        dayOfWeek.name
        ,
        Modifier
            .height(24.dp)
            .padding(start = 8.dp, end = 24.dp),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun HoursHeader(hours: List<Int>) {
    val brushColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary
    )
    Row(
        Modifier
            .padding(bottom = 16.dp)
            .drawBehind {
                val brush = Brush.linearGradient(brushColors)
                drawRoundRect(
                    brush,
                    cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                )
            }
    ) {
        hours.forEach {
            Text(
                text = "$it",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(50.dp)
                    .padding(vertical = 4.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
