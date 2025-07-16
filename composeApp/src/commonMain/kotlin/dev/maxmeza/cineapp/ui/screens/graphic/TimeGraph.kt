package dev.maxmeza.cineapp.ui.screens.graphic

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.math.roundToInt


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimeGraph(
    hoursHeader: @Composable () -> Unit,
    dayItemsCount: Int,
    dayLabel: @Composable (index: Int) -> Unit,
    bar: @Composable TimeGraphScope.(index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dayLabels = @Composable { repeat(dayItemsCount) { dayLabel(it) } }
    val bars = @Composable { repeat(dayItemsCount) { TimeGraphScope.bar(it) } }
    Layout(
        contents = listOf(hoursHeader, dayLabels, bars),
        modifier = modifier.padding(bottom = 32.dp)
    ) {
            (hoursHeaderMeasurables, dayLabelMeasurables, barMeasureables),
            constraints,
        ->
        require(hoursHeaderMeasurables.size == 1) {
            "hoursHeader should only emit one composable"
        }
        val hoursHeaderPlaceable = hoursHeaderMeasurables.first().measure(constraints)

        val dayLabelPlaceables = dayLabelMeasurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            placeable
        }

        var totalHeight = hoursHeaderPlaceable.height

        val barPlaceables = barMeasureables.map { measurable ->
            val barParentData = measurable.parentData as TimeGraphParentData
            val barWidth = (barParentData.duration * hoursHeaderPlaceable.width).roundToInt()

            val barPlaceable = measurable.measure(
                constraints.copy(
                    minWidth = barWidth,
                    maxWidth = barWidth
                )
            )
            totalHeight += barPlaceable.height
            barPlaceable
        }

        val totalWidth = dayLabelPlaceables.first().width + hoursHeaderPlaceable.width

        layout(totalWidth, totalHeight) {
            val xPosition = dayLabelPlaceables.first().width
            var yPosition = hoursHeaderPlaceable.height

            hoursHeaderPlaceable.place(xPosition, 0)

            barPlaceables.forEachIndexed { index, barPlaceable ->
                val barParentData = barPlaceable.parentData as TimeGraphParentData
                val barOffset = (barParentData.offset * hoursHeaderPlaceable.width).roundToInt()

                barPlaceable.place(xPosition + barOffset, yPosition)
                // the label depend on the size of the bar content - so should use the same y
                val dayLabelPlaceable = dayLabelPlaceables[index]
                dayLabelPlaceable.place(x = 0, y = yPosition)

                yPosition += barPlaceable.height
            }
        }
    }
}

@LayoutScopeMarker
@Immutable
object TimeGraphScope {
    @Stable
    fun Modifier.timeGraphBar(
        start: LocalDateTime,
        end: LocalDateTime,
        hours: List<Int>,
    ): Modifier {
        val totalHourSpan = hours.size

        val durationInMinutes = start.minutesUntil(end)
        val durationInHours = durationInMinutes / 60f

        val earliestTime = LocalTime(hour = hours.first(), minute = 0)
        val offsetInMinutes = earliestTime.minutesUntil(start.toLocalTime())
        val offsetInHours = offsetInMinutes / 60f + 0.5f // for centering label

        return then(
            TimeGraphParentData(
                duration = durationInHours / totalHourSpan,
                offset = offsetInHours / totalHourSpan
            )
        )
    }
}

class TimeGraphParentData(
    val duration: Float,
    val offset: Float,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@TimeGraphParentData
}

fun LocalTime.minutesUntil(other: LocalTime): Int {
    val thisMinutes = this.hour * 60 + this.minute
    val otherMinutes = other.hour * 60 + other.minute
    return otherMinutes - thisMinutes
}

fun LocalDateTime.minutesUntil(other: LocalDateTime): Int {
    val thisMinutes = this.hour * 60 + this.minute + this.dayOfYear * 24 * 60
    val otherMinutes = other.hour * 60 + other.minute + other.dayOfYear * 24 * 60
    return otherMinutes - thisMinutes
}

fun LocalDateTime.toLocalTime(): LocalTime {
    return LocalTime(this.hour, this.minute)
}