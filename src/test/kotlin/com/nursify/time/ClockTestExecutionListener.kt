package com.nursify.time

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener
import java.time.Clock
import java.time.ZoneId

class ClockTestExecutionListener : AbstractTestExecutionListener(), BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        com.nursify.ClockHolder.clockImpl = TestClockHolder.CLOCK
        com.nursify.ClockHolder.timeZone = TestClockHolder.timeZone
    }

    override fun afterEach(context: ExtensionContext) {
        com.nursify.ClockHolder.clockImpl = Clock.system(com.nursify.ClockHolder.timeZone)
        com.nursify.ClockHolder.timeZone = ZoneId.systemDefault()
    }

    override fun beforeTestMethod(testContext: TestContext) {
        com.nursify.ClockHolder.clockImpl = TestClockHolder.CLOCK
        com.nursify.ClockHolder.timeZone = TestClockHolder.timeZone
    }

    override fun afterTestMethod(testContext: TestContext) {
        com.nursify.ClockHolder.clockImpl = Clock.system(com.nursify.ClockHolder.timeZone)
        com.nursify.ClockHolder.timeZone = ZoneId.systemDefault()
    }
}
