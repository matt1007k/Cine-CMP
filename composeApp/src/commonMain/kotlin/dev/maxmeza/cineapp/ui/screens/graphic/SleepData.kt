package dev.maxmeza.cineapp.ui.screens.sleep

import cineapp.composeapp.generated.resources.Res
import cineapp.composeapp.generated.resources.sleep_type_awake
import cineapp.composeapp.generated.resources.sleep_type_deep
import cineapp.composeapp.generated.resources.sleep_type_light
import cineapp.composeapp.generated.resources.sleep_type_rem
import kotlinx.datetime.*
import org.jetbrains.compose.resources.StringResource
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

data class SleepGraphData(
    val sleepDayData: List<SleepDayData>,
) {
    val earliestStartHour: Int by lazy {
        sleepDayData.minOf { it.firstSleepStart.hour }
    }
    val latestEndHour: Int by lazy {
        sleepDayData.maxOf { it.lastSleepEnd.hour }
    }
}

data class SleepDayData(
    val startDate: LocalDateTime,
    val sleepPeriods: List<SleepPeriod>,
    val sleepScore: Int,
) {
    val zone = TimeZone.currentSystemDefault()
    val firstSleepStart: LocalDateTime by lazy {
        sleepPeriods.sortedBy(SleepPeriod::startTime).first().startTime
    }
    val lastSleepEnd: LocalDateTime by lazy {
        sleepPeriods.sortedBy(SleepPeriod::startTime).last().endTime
    }
    @OptIn(ExperimentalTime::class)
    val totalTimeInBed: Duration by lazy {
        val startInstant = firstSleepStart.toInstant(zone)
        val endInstant = lastSleepEnd.toInstant(zone)
        (endInstant - startInstant)
    }

    val sleepScoreEmoji: String by lazy {
        when (sleepScore) {
            in 0..40 -> "😖"
            in 41..60 -> "😏"
            in 60..70 -> "😴"
            in 71..100 -> "😃"
            else -> "🤷‍"
        }
    }

    fun fractionOfTotalTime(sleepPeriod: SleepPeriod): Float {
        return sleepPeriod.duration.toDateTimePeriod().minutes / totalTimeInBed.toDateTimePeriod().minutes.toFloat()
    }

    @OptIn(ExperimentalTime::class)
    fun minutesAfterSleepStart(sleepPeriod: SleepPeriod): Long {
    val zone = TimeZone.currentSystemDefault()
        val duration = sleepPeriod.startTime.toInstant(zone) - firstSleepStart.toInstant(zone)
        return duration.inWholeMinutes
    }
}

data class SleepPeriod(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val type: SleepType,
) {
    val zone = TimeZone.currentSystemDefault()
    @OptIn(ExperimentalTime::class)
    val duration: Duration by lazy {
        val startInstant = startTime.toInstant(zone)
        val endInstant = endTime.toInstant(zone)
        (endInstant - startInstant)
    }
}

enum class SleepType(val title: StringResource) {
    Awake(Res.string.sleep_type_awake),
    REM(Res.string.sleep_type_rem),
    Light(Res.string.sleep_type_light),
    Deep(Res.string.sleep_type_deep)
}
