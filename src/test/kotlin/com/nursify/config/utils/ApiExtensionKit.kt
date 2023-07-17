package com.nursify.config.utils

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun stringToOffsetDateTime(date: String): OffsetDateTime {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(
        com.nursify.ClockHolder.timeZone
    ).toOffsetDateTime()
}