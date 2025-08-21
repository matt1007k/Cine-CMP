package dev.maxmeza.cineapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

actual class DateUtil {
    @RequiresApi(Build.VERSION_CODES.O)
    actual fun formatDate(datetime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = ZonedDateTime.parse(datetime)
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    actual fun getDateToJSONString(): String {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return date.format(formatter)
    }
}