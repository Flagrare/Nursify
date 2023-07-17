package com.nursify

import java.time.Clock
import java.time.ZoneId

object ClockHolder {
    val CLOCK: Clock = object : Clock() {
        override fun withZone(zone: ZoneId?) = com.nursify.ClockHolder.clockImpl.withZone(zone)
        override fun getZone() = com.nursify.ClockHolder.clockImpl.zone
        override fun instant() = com.nursify.ClockHolder.clockImpl.instant()
    }
    var timeZone: ZoneId = ZoneId.systemDefault()
    var clockImpl: Clock = Clock.system(com.nursify.ClockHolder.timeZone)
}