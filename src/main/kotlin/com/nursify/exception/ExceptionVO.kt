package com.nursify.exception

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset


data class ExceptionVO(
    @Schema(description = "Error type", example = "RESOURCE_NOT_FOUND")
    @get:JsonProperty("type") val type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
    @Schema(description = "Error message", example = "Shifts for [ some_facility_name ] not found")
    @get:JsonProperty("message") val message: String = type.message,
    @Schema(description = "Error details", example = "")
    @get:JsonProperty("details") val details: String? = null,
    @Schema(description = "Timestamp", example = "")
    @get:JsonProperty("timestamp") val timestamp: OffsetDateTime? = Instant.now(com.nursify.ClockHolder.CLOCK)
        .atOffset(ZoneOffset.UTC),
    @Schema(description = "Endpoint request", example = "")
    @get:JsonProperty("request") val request: String? = null
)