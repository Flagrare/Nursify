package com.nursify.config

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

object TestContainers {

    @Container
    var postgresqlContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15")
        .withDatabaseName("postgres")
        .withUsername("postgres")
        .withPassword("postgres")

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            postgresqlContainer.withInitScript("db/migration.sql")
            postgresqlContainer.start()
        }
    }
}