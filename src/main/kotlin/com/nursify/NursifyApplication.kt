package com.nursify

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean


@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
class NursifyApplication {
    @Bean
    fun clock() = com.nursify.ClockHolder.CLOCK

    @Bean
    fun configurer(
        @Value("\${spring.application.name}") applicationName: String?
    ): MeterRegistryCustomizer<MeterRegistry>? {
        return MeterRegistryCustomizer { registry: MeterRegistry ->
            registry.config().commonTags("application", applicationName)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<com.nursify.NursifyApplication>(*args)
}
