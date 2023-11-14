package com.example.moviecatalog.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun convertDateStringToLocalDate(dateString: String): LocalDate? {
    val formatterWithMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[XXX][X]")
    return try {
        val parsedDateTime = LocalDateTime.parse(dateString, formatterWithMillis)
        parsedDateTime.toLocalDate()
    } catch (e: Exception) {
        val formatterWithoutMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        try {
            val parsedDateTime = LocalDateTime.parse(dateString, formatterWithoutMillis)
            parsedDateTime.toLocalDate()
        } catch (e: Exception) {
            null
        }
    }
}

fun LocalDate.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    return this.atStartOfDay().format(formatter)
}
