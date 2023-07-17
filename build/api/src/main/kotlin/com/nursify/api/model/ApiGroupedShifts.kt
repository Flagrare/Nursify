package com.nursify.api.model

import java.util.Objects
import com.nursify.api.model.ApiShift
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
 * @param start 
 * @param shifts 
 */
data class ApiGroupedShifts(

    @field:Valid
    @Schema(example = "null", description = "")
    @get:JsonProperty("start") val start: java.time.LocalDate? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @get:JsonProperty("shifts") val shifts: kotlin.collections.List<com.nursify.api.model.ApiShift>? = null
) {

}

