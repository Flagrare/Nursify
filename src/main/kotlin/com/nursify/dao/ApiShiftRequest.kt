package com.nursify.dao

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

data class ApiShiftRequest(
    @Schema(example = "null", required = false, description = "")
    @get:JsonProperty("id", required = true) val id: Long? = null,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("start", required = true) val start: java.time.OffsetDateTime,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("end", required = true) val end: java.time.OffsetDateTime,

    @field:Valid
    @Schema(example = "null", required = false, description = "")
    @get:JsonProperty("facilityId", required = false) val facilityId: Long? = null,

    @field:Valid
    @Schema(example = "null", required = false, description = "")
    @get:JsonProperty("workerId", required = false) val workerId: Long? = null
)
