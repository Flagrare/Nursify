package com.nursify.api

import com.nursify.api.model.ApiAvailableShifts
import com.nursify.api.model.ApiWorkerShiftRange
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.enums.*
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.security.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.beans.factory.annotation.Autowired

import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

import kotlin.collections.List
import kotlin.collections.Map

@RestController
@Validated
@RequestMapping("\${api.base-path:/api/v1}")
class ShiftsApiController(@Autowired(required = true) val service: com.nursify.api.ShiftsApiService) {

    @Operation(
        summary = "Gets shifts from worker",
        operationId = "getWorkerShifts",
        description = """""",
        responses = [
            ApiResponse(responseCode = "200", description = "Shift info", content = [Content(schema = Schema(implementation = com.nursify.api.model.ApiAvailableShifts::class))]),
            ApiResponse(responseCode = "204", description = "No shifts available") ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/shifts/{workerId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getWorkerShifts(@Parameter(description = "", required = true) @PathVariable("workerId") workerId: kotlin.Long,@Parameter(description = "") @Valid @RequestParam(value = "facilityId", required = false) facilityId: kotlin.Long?,@Parameter(description = "", schema = Schema(defaultValue = "false")) @Valid @RequestParam(value = "searchFromPast", required = false, defaultValue = "false") searchFromPast: kotlin.Boolean,@Min(0)@Parameter(description = "", schema = Schema(defaultValue = "0")) @Valid @RequestParam(value = "pageNumber", required = false, defaultValue = "0") pageNumber: kotlin.Int,@Min(0)@Parameter(description = "", schema = Schema(defaultValue = "20")) @Valid @RequestParam(value = "pageSize", required = false, defaultValue = "20") pageSize: kotlin.Int,@Parameter(description = "According to the Spring Pageable format the field name must be followed by the sortDirection. Example: [ 'start;desc', 'end;asc', 'facility.id;desc ]") @Valid @RequestParam(value = "sort", required = false) sort: kotlin.collections.List<kotlin.String>?,@Parameter(description = "") @Valid @RequestBody(required = false) apiWorkerShiftRange: com.nursify.api.model.ApiWorkerShiftRange?): ResponseEntity<com.nursify.api.model.ApiAvailableShifts> {
        return ResponseEntity(service.getWorkerShifts(workerId, facilityId, searchFromPast, pageNumber, pageSize, sort, apiWorkerShiftRange), HttpStatus.valueOf(200))
    }
}
