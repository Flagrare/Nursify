package com.nursify.api.model

import java.util.Objects
import com.nursify.api.model.ApiGroupedShifts
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
 * @param content 
 * @param pageNumber 
 * @param pageSize 
 * @param numberOfElements 
 * @param totalPages 
 * @param totalElements 
 * @param isLast 
 */
data class ApiAvailableShifts(

    @field:Valid
    @Schema(example = "null", description = "")
    @get:JsonProperty("content") val content: kotlin.collections.List<com.nursify.api.model.ApiGroupedShifts>? = null,

    @get:Min(0)
    @Schema(example = "null", description = "")
    @get:JsonProperty("pageNumber") val pageNumber: kotlin.Int? = null,

    @get:Min(10)
    @Schema(example = "null", description = "")
    @get:JsonProperty("pageSize") val pageSize: kotlin.Int? = null,

    @Schema(example = "null", description = "")
    @get:JsonProperty("numberOfElements") val numberOfElements: kotlin.Int? = null,

    @Schema(example = "null", description = "")
    @get:JsonProperty("totalPages") val totalPages: kotlin.Int? = null,

    @Schema(example = "null", description = "")
    @get:JsonProperty("totalElements") val totalElements: kotlin.Long? = null,

    @Schema(example = "null", description = "")
    @get:JsonProperty("isLast") val isLast: kotlin.Boolean? = null
) {

}

