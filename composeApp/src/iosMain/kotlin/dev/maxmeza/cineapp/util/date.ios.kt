package dev.maxmeza.cineapp.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSISO8601DateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.autoupdatingCurrentLocale
import platform.Foundation.localTimeZone

actual class DateUtil {
    actual fun formatDate(datetime: String): String {
        println("datetime: $datetime")
        val date = NSISO8601DateFormatter().dateFromString(string = datetime)
        val formatter = NSDateFormatter().apply {
            dateFormat = "dd-MM-yyyy HH:mm:ss"
            timeZone = NSTimeZone.localTimeZone
            locale = NSLocale.autoupdatingCurrentLocale
        }
        println("Formatted date: $date")
//        val date = NSDate(datetime.toDouble() / 1000)
        return if(date != null) formatter.stringFromDate(date) else ""
    }

    actual fun getDateToJSONString(): String {
        val dateFormat = NSDateFormatter().apply {
            dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
            timeZone = NSTimeZone.localTimeZone
        }
        val date = NSDate()
        return dateFormat.stringFromDate(date)
    }
}