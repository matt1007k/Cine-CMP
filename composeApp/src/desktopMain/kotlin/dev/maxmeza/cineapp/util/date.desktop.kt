package dev.maxmeza.cineapp.util

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

actual class DateUtil {
    actual fun formatDate(datetime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .withZone(ZoneOffset.UTC)
        val date = ZonedDateTime.parse(datetime)
        return date.format(formatter)
    }

    actual fun getDateToJSONString(): String {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return date.format(formatter)
    }
}