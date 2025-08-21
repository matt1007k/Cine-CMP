package dev.maxmeza.cineapp.util

expect class DateUtil() {
   fun formatDate(datetime: String): String
   fun getDateToJSONString(): String
}