@file:OptIn(ExperimentalTime::class)

package dev.maxmeza.cineapp.data.local

import dev.maxmeza.cineapp.ui.screens.sleep.SleepDayData
import dev.maxmeza.cineapp.ui.screens.sleep.SleepGraphData
import dev.maxmeza.cineapp.ui.screens.sleep.SleepPeriod
import dev.maxmeza.cineapp.ui.screens.sleep.SleepType
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun LocalDateTime.minusPeriod(
    period: DateTimePeriod,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    val instant = this.toInstant(timeZone)
    val adjusted = instant.minus(period, timeZone)
    return adjusted.toLocalDateTime(timeZone)
}

fun daysAgo(days: Int, hour: Int = 0, minute: Int = 0): LocalDateTime {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val newDate = now.date.minus(DatePeriod(days = days))
    val newTime = LocalTime(hour, minute)
    return LocalDateTime(newDate, newTime)
}

val sleepData = SleepGraphData(
    listOf(
        SleepDayData(
            startDate = daysAgo(days = 7),
            sleepPeriods = listOf(
                SleepPeriod(
                    startTime = daysAgo(days = 7, hour = 21, minute = 8)
                       ,
                    endTime =
                        daysAgo(days = 7, hour = 21, minute = 40)
                       ,
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime =
                        daysAgo(days = 7, hour = 21, minute = 40)
                                ,
                    endTime = daysAgo(days = 7, hour = 22, minute = 20)
                            ,
                    type = SleepType.Light
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 7, hour = 22, minute = 20)
                        ,
                    endTime =
                        daysAgo(days = 7, hour = 22, minute = 50),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 7, hour = 22, minute = 50),
                    endTime = daysAgo(days = 7, hour = 23, minute = 30),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 7, hour = 23, minute = 30),
                    endTime = daysAgo(days = 6, hour = 1, minute = 10),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 6, hour = 1, minute = 10),
                    endTime = daysAgo(days = 6, hour = 2, minute = 30),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 6, hour = 2, minute = 30),
                    endTime = daysAgo(days = 6, hour = 4, minute = 10),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 6, hour = 4, minute = 10),
                    endTime = daysAgo(days = 6, hour = 5, minute = 30),
                    type = SleepType.Awake
                )
            ),
            sleepScore = 90
        ),
        SleepDayData(
            daysAgo(days = 6),
            listOf(
                SleepPeriod(
                    startTime = daysAgo(days = 6, hour = 22, minute = 38),
                    endTime = daysAgo(days = 6, hour = 22, minute = 50),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 6, hour = 22, minute = 50),
                    endTime = daysAgo(days = 6, hour = 23, minute = 30),
                    type = SleepType.Light
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 6, hour = 23, minute = 30),
                    endTime = daysAgo(days = 6, hour = 23, minute = 55),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 6, hour = 23, minute = 55),
                    endTime = daysAgo(days = 5, hour = 2, minute = 40),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 5, hour = 2, minute = 40),
                    endTime = daysAgo(days = 5, hour = 2, minute = 50),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 5, hour = 2, minute = 50),
                    endTime = daysAgo(days = 5, hour = 4, minute = 12),
                    type = SleepType.Deep
                )
            ),
            sleepScore = 70
        ),
        SleepDayData(
            daysAgo(days = 5),
            listOf(
                SleepPeriod(
                    startTime = daysAgo(days = 5, hour = 22, minute = 8),
                    endTime = daysAgo(days = 5, hour = 22, minute = 40),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 5, hour = 22, minute = 40),
                    endTime = daysAgo(days = 5, hour = 22, minute = 50),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 5, hour = 22, minute = 50),
                    endTime = daysAgo(days = 5, hour = 22, minute = 55),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 5, hour = 22, minute = 55),
                    endTime = daysAgo(days = 5, hour = 23, minute = 30),
                    type = SleepType.Light
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 5, hour = 23, minute = 30),
                    endTime = daysAgo(days = 4, hour = 1, minute = 10),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 1, minute = 10),
                    endTime = daysAgo(days = 4, hour = 2, minute = 30),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 2, minute = 30),
                    endTime = daysAgo(days = 4, hour = 3, minute = 5),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 3, minute = 5),
                    endTime = daysAgo(days = 4, hour = 4, minute = 50),
                    type = SleepType.Light
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 4, minute = 50),
                    endTime = daysAgo(days = 4, hour = 6, minute = 30),
                    type = SleepType.REM
                )
            ),
            sleepScore = 60
        ),
        SleepDayData(
            daysAgo(days = 4),
            listOf(
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 20, minute = 20),
                    endTime = daysAgo(days = 4, hour = 22, minute = 40),
                    type = SleepType.Light
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 22, minute = 40),
                    endTime = daysAgo(days = 4, hour = 22, minute = 50),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 22, minute = 50),
                    endTime = daysAgo(days = 4, hour = 23, minute = 55),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 4, hour = 23, minute = 55),
                    endTime = daysAgo(days = 3, hour = 1, minute = 33),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 3, hour = 1, minute = 33),
                    endTime = daysAgo(days = 3, hour = 2, minute = 30),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 3, hour = 2, minute = 30),
                    endTime = daysAgo(days = 3, hour = 3, minute = 45),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 3, hour = 3, minute = 45),
                    endTime = daysAgo(days = 3, hour = 7, minute = 15),
                    type = SleepType.Light
                )
            ),
            sleepScore = 90
        ),
        SleepDayData(
            daysAgo(days = 3),
            listOf(
                SleepPeriod(
                    startTime = daysAgo(days = 3, hour = 22, minute = 50),
                    endTime = daysAgo(days = 3, hour = 23, minute = 30),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 3, hour = 23, minute = 30),
                    endTime = daysAgo(days = 2, hour = 0, minute = 10),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 0, minute = 10),
                    endTime = daysAgo(days = 2, hour = 1, minute = 10),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 1, minute = 10),
                    endTime = daysAgo(days = 2, hour = 2, minute = 30),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 2, minute = 30),
                    endTime = daysAgo(days = 2, hour = 4, minute = 30),
                    type = SleepType.Light
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 4, minute = 30),
                    endTime = daysAgo(days = 2, hour = 4, minute = 45),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 4, minute = 30),
                    endTime = daysAgo(days = 2, hour = 4, minute = 45),
                    type = SleepType.REM
                )
            ),
            sleepScore = 40
        ),
        SleepDayData(
            daysAgo(days = 2),
            listOf(
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 20, minute = 40),
                    endTime = daysAgo(days = 2, hour = 21, minute = 40),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 21, minute = 40),
                    endTime = daysAgo(days = 2, hour = 22, minute = 20),
                    type = SleepType.Light
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 22, minute = 20),
                    endTime = daysAgo(days = 2, hour = 22, minute = 50),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 22, minute = 50),
                    endTime = daysAgo(days = 2, hour = 23, minute = 30),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 2, hour = 23, minute = 30),
                    endTime = daysAgo(days = 1, hour = 1, minute = 10),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 1, minute = 10),
                    endTime = daysAgo(days = 1, hour = 2, minute = 30),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 2, minute = 30),
                    endTime = daysAgo(days = 1, hour = 4, minute = 10),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 4, minute = 10),
                    endTime = daysAgo(days = 1, hour = 5, minute = 30),
                    type = SleepType.Awake
                )
            ),
            sleepScore = 82
        ),
        SleepDayData(
            daysAgo(days = 1),
            listOf(
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 22, minute = 8),
                    endTime = daysAgo(days = 1, hour = 22, minute = 40),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 22, minute = 40),
                    endTime = daysAgo(days = 1, hour = 22, minute = 50),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 22, minute = 50),
                    endTime = daysAgo(days = 1, hour = 22, minute = 55),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 22, minute = 55),
                    endTime = daysAgo(days = 1, hour = 23, minute = 30),
                    type = SleepType.REM
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 1, hour = 23, minute = 30),
                    endTime = daysAgo(days = 0, hour = 1, minute = 10),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 0, hour = 1, minute = 10),
                    endTime = daysAgo(days = 0, hour = 2, minute = 30),
                    type = SleepType.Awake
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 0, hour = 2, minute = 30),
                    endTime = daysAgo(days = 0, hour = 3, minute = 5),
                    type = SleepType.Deep
                ),
                SleepPeriod(
                    startTime = daysAgo(days = 0, hour = 3, minute = 5),
                    endTime = daysAgo(days = 0, hour = 4, minute = 50),
                    type = SleepType.Light
                )
            ),
            sleepScore = 70
        ),
    )
)
