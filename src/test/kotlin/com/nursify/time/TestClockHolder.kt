package com.nursify.time

import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.random.Random

object TestClockHolder {
    private val NOW: Instant = Instant.EPOCH.plus(365 * 50L, ChronoUnit.DAYS)
        .plusSeconds(Random.nextLong(0, 500)).truncatedTo(ChronoUnit.SECONDS)
    val timeZone: ZoneId = ZoneId.of("GMT")
    internal val CLOCK: Clock = Clock.fixed(NOW, timeZone)
    val NOW_DATE_TIME_OFFSET: OffsetDateTime = OffsetDateTime.ofInstant(NOW, timeZone)

    fun getRandomInstant(): Instant = Instant.EPOCH.plus(365 * 50L, ChronoUnit.DAYS)
        .plusSeconds(Random.nextLong(0, 500)).truncatedTo(ChronoUnit.SECONDS)

    fun getRandomOffsetDateTime(): OffsetDateTime = OffsetDateTime.ofInstant(getRandomInstant(), timeZone)
}
