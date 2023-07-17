package com.nursify.controller

import clipboardhealth.challenge.nursify.config.utils.stringToOffsetDateTime
import clipboardhealth.challenge.nursify.dao.ApiPaginationObjectTest
import clipboardhealth.challenge.nursify.dao.ApiShiftRangeRequestTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@DisplayName("[Shift] Api Controller Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("qa")
class ShiftApiControllerTest(
    @Autowired private val shiftsController: com.nursify.api.ShiftsApiController,
) {

    companion object {
        val sortObject = ApiPaginationObjectTest(
            listOf("start;asc"),
            0,
            20
        )
    }

    @DisplayName("can get a list of shifts filtered by a date range")
    @Test
    fun getDateRangeShifts() {
        val shiftsResponse = validShiftRequest()

        assertTrue(shiftsResponse.statusCode.is2xxSuccessful)
        val apiAvailableShifts = shiftsResponse.body?.content
        assertThat(apiAvailableShifts).isNotEmpty()
    }

    @DisplayName("can get a list of shifts grouped by date")
    @Test
    fun getShiftsGrouped() {
        val shiftsResponse = validShiftRequest()

        assertTrue(shiftsResponse.statusCode.is2xxSuccessful)
        val apiAvailableShifts = shiftsResponse.body?.content

        apiAvailableShifts?.map { it ->
            val fields = it.javaClass.declaredFields

            val hasFieldOfTypeDate = fields.any { field ->
                field.type == LocalDate::class.java || LocalDate::class.java.isAssignableFrom(field.type)
            }
            val hasFieldOfTypeList = fields.any { field ->
                field.type == Collection::class.java || Collection::class.java.isAssignableFrom(field.type)
            }
            assertThat(hasFieldOfTypeDate).isTrue()
            assertThat(hasFieldOfTypeList).isTrue()
        }

        assertThat(apiAvailableShifts).isNotEmpty()
    }

    @DisplayName("should not get a list of shifts filtered by a date range if is searching a past date")
    @Test
    fun getInvalidPastDateRangeShifts() {
        val shiftRange = ApiShiftRangeRequestTest(
            startDate = "2023-02-15",
            endDate = "2023-02-21"
        )
        assertThrows<Exception> {
            shiftsController.getWorkerShifts(
                101,
                null,
                false,
                sortObject.pageNumber,
                sortObject.pageSize,
                sortObject.sort,
                apiWorkerShiftRange = com.nursify.api.model.ApiWorkerShiftRange(
                    startDate = stringToOffsetDateTime(shiftRange.startDate),
                    endDate = stringToOffsetDateTime(shiftRange.endDate)
                )
            )
        }
    }

    @DisplayName("should not get a list of shifts filtered by an invalid date range [end<start]")
    @Test
    fun getInvalidDateRangeShifts() {
        val shiftRange = ApiShiftRangeRequestTest(
            startDate = "2023-02-21",
            endDate = "2023-02-15"
        )
        assertThrows<Exception> {
            shiftsController.getWorkerShifts(
                101,
                null,
                false,
                sortObject.pageNumber,
                sortObject.pageSize,
                sortObject.sort,
                apiWorkerShiftRange = com.nursify.api.model.ApiWorkerShiftRange(
                    startDate = stringToOffsetDateTime(shiftRange.startDate),
                    endDate = stringToOffsetDateTime(shiftRange.endDate)
                )
            )
        }
    }

    @DisplayName("can get a list of shifts for a worker without a date range")
    @Test
    fun getWorkerShifts() {

        val shiftsResponse = shiftsController.getWorkerShifts(
            101,
            null,
            false,
            sortObject.pageNumber,
            sortObject.pageSize,
            sortObject.sort,
            null
        )

        assertTrue(shiftsResponse.statusCode.is2xxSuccessful)
        val apiAvailableShifts = shiftsResponse.body?.content
        assertThat(apiAvailableShifts).isNotEmpty()
    }

    @DisplayName("can get a list of shifts for a worker in a specific active facility")
    @Test
    fun getFacilityWorkerShifts() {

        val shiftsResponse = shiftsController.getWorkerShifts(
            101,
            3,
            false,
            sortObject.pageNumber,
            sortObject.pageSize,
            sortObject.sort,
            null
        )

        assertTrue(shiftsResponse.statusCode.is2xxSuccessful)
        val apiAvailableShifts = shiftsResponse.body?.content
        assertThat(apiAvailableShifts).isNotEmpty()
    }

    @DisplayName("should not get a list of shifts for a worker in a specific inactive facility")
    @Test
    fun getInactiveFacilityWorkerShifts() {

        val shiftsResponse = shiftsController.getWorkerShifts(
            101,
            1,
            false,
            sortObject.pageNumber,
            sortObject.pageSize,
            sortObject.sort,
            null
        )

        assertTrue(shiftsResponse.statusCode.is2xxSuccessful)
        val apiAvailableShifts = shiftsResponse.body?.content
        assertThat(apiAvailableShifts).isEmpty()
    }

    private fun validShiftRequest(): ResponseEntity<com.nursify.api.model.ApiAvailableShifts> {
        val shiftRange = ApiShiftRangeRequestTest(
            startDate = "2023-02-15",
            endDate = "2023-02-21"
        )

        val shiftsResponse = shiftsController.getWorkerShifts(
            101,
            null,
            true,
            sortObject.pageNumber,
            sortObject.pageSize,
            sortObject.sort,
            apiWorkerShiftRange = com.nursify.api.model.ApiWorkerShiftRange(
                startDate = stringToOffsetDateTime(shiftRange.startDate),
                endDate = stringToOffsetDateTime(shiftRange.endDate)
            )
        )
        return shiftsResponse
    }
}

