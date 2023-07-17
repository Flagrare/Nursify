package com.nursify

import clipboardhealth.challenge.nursify.config.TestContainers
import clipboardhealth.challenge.nursify.exception.RestResponseEntityExceptionHandler
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.testcontainers.junit.jupiter.Testcontainers

@DisplayName("{NURSIFY} [Application] Test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
@SpringJUnitConfig(
    initializers = [TestContainers.Initializer::class],
)
class NursifyApplicationTests {
    companion object {
        private val log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler::class.java)
    }

    @Test
    @DisplayName("context should load")
    fun contextLoads() {
        log.info("If this line is printed, the context loads perfectly")
    }

}
