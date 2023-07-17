package com.nursify.api.model

import java.util.Objects
import com.nursify.api.model.ApiFacility
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import jakarta.validation.Valid
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 
 * @param id 
 * @param start 
 * @param end 
 * @param facility 
 */
data class ApiShift(

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("id", required = true) val id: kotlin.Long,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("start", required = true) val start: java.time.OffsetDateTime,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("end", required = true) val end: java.time.OffsetDateTime,

    @field:Valid
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("facility", required = true) val facility: com.nursify.api.model.ApiFacility
) {

}

