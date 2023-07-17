package com.nursify.api

import clipboardhealth.challenge.nursify.config.utils.stringToOffsetDateTime
import clipboardhealth.challenge.nursify.dao.ApiPaginationObjectTest
import clipboardhealth.challenge.nursify.dao.ApiShiftQueryPayload
import clipboardhealth.challenge.nursify.dao.ApiShiftRangeRequestOffset
import clipboardhealth.challenge.nursify.dao.toMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate


@DisplayName("[Shift] REST Api Tests")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("qa")
class ShiftsApiControllerTest {
    @Autowired
    private val restTemplate: TestRestTemplate? = null

    private val apiUrl = "/api/v1"

    private val sortObject = ApiPaginationObjectTest(
        listOf("start;asc"),
        0,
        20
    )

    private val activeFacility = 1L
    private val activeFacilityWithShifts = 3L
    private val inactiveFacilityId = 2L
    private val activeWorkerCNA = 101L
    private val unmatchedActiveWorkerCNA = 11L
    private val inactiveWorkerIdCNA = 2L

    @Test
    @DisplayName("should not get list of shifts for inactive facility")
    fun shouldNotGetShiftsForInactiveFacility() {

        val shiftPayload = validShiftPayload(inactiveFacilityId, true).toMap()

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            null,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload,
        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body).isNotNull
        assertThat(response.body?.content).hasSize(0)
    }

    @Test
    @DisplayName("should not get list of shifts for inactive worker")
    fun shouldNotGetShiftsForInactiveWorker() {

        val shiftPayload = validShiftPayload(null, true).toMap()

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(inactiveWorkerIdCNA),
            null,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload,
        )

        assertTrue(response.statusCode.is4xxClientError)
    }


    @Test
    @DisplayName("should not get list of shifts filtered by past date")
    fun shouldNotGetShiftsFilteredByPastDate() {
        val shiftPayload = validShiftPayload(null, false).toMap()
        val shiftDate = shiftDatePayload("2023-02-15", "2023-02-21")

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            shiftDate,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload,
        )
        assertTrue(response.statusCode.is4xxClientError)

    }

    @Test
    @DisplayName("can get list of shifts filtered by date range")
    fun canGetShiftsFilteredByDateRange() {
        val shiftPayload = validShiftPayload(null, true).toMap()
        val shiftDate = shiftDatePayload("2023-02-15", "2023-02-21")

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            shiftDate,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload

        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body?.content).isNotEmpty
        assertThat(response.body?.content?.map {
            assertThat(it.shifts).isNotEmpty
            assertTrue(shiftDate.startDate.toLocalDate() <= it.start)
            assertTrue(shiftDate.endDate.toLocalDate() >= it.start)
        })
    }

    @Test
    @DisplayName("should return shifts grouped by date")
    fun canGetListOfShiftsGroupedByDate() {
        val shiftPayload = validShiftPayload(null, true).toMap()
        val shiftDate = shiftDatePayload("2023-02-15", "2023-02-21")


        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            shiftDate,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload
        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body?.content).isNotEmpty
        assertThat(response.body?.content?.map {
            assertThat(it.shifts).isNotEmpty
            val fields = it.javaClass.declaredFields
            val hasFieldOfTypeDate = fields.any { field ->
                field.type == LocalDate::class.java || LocalDate::class.java.isAssignableFrom(field.type)
            }
            assertThat(hasFieldOfTypeDate).isTrue()
        })
    }

    @Test
    fun canGetListOfShiftsForWorkerInActiveFacility() {
        val shiftPayload = validShiftPayload(activeFacilityWithShifts, true).toMap()

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            null,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload

        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body?.content).isNotEmpty
        assertThat(response.body?.content?.map {
            assertThat(it.shifts).isNotEmpty
            val fields = it.javaClass.declaredFields

            val hasFieldOfTypeDate = fields.any { field ->
                field.type == LocalDate::class.java || LocalDate::class.java.isAssignableFrom(field.type)
            }
            assertThat(hasFieldOfTypeDate).isTrue()

        })
    }

    @Test
    @DisplayName("can get a list of shifts filtered by a date range and facility")
    fun canGetShiftsFromFacilityWithValidDateRange() {
        val shiftPayload = validShiftPayload(activeFacilityWithShifts, true).toMap()
        val shiftDate = shiftDatePayload("2023-02-15", "2023-02-21")

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            shiftDate,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload

        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body?.content).isNotEmpty
        assertThat(response.body?.content?.map {
            assertThat(it.shifts).isNotEmpty
            val fields = it.javaClass.declaredFields

            val hasFieldOfTypeDate = fields.any { field ->
                field.type == LocalDate::class.java || LocalDate::class.java.isAssignableFrom(field.type)
            }
            assertTrue(shiftDate.startDate.toLocalDate() <= it.start)
            assertTrue(shiftDate.endDate.toLocalDate() >= it.start)
            assertThat(hasFieldOfTypeDate).isTrue()

        })
    }

    @Test
    @DisplayName("should return empty shifts from facility with no matching documents")
    fun shouldReturnEmptyShiftsFromFacilityWithUnMatchingDocuments() {
        val shiftPayload = validShiftPayload(activeFacility, true).toMap()

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            null,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload

        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body?.content).isEmpty()
    }

    @Test
    @DisplayName("shoudl return empty shifts for worker with no matching documents with any facility")
    fun shouldReturnEmptyShiftsForWorkerWithUnMatchingDocuments() {
        val shiftPayload = validShiftPayload(null, true).toMap()

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(unmatchedActiveWorkerCNA),
            null,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload

        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body?.content).isEmpty()
    }

    @Test
    @DisplayName("should not get list of shifts filtered by invalid date range")
    fun shouldNotGetListOfShiftsFilteredByInvalidDateRange() {

        val shiftPayload = validShiftPayload(null, true).toMap()
        val shiftDate = shiftDatePayload("2023-02-21", "2023-02-15")

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            shiftDate,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload
        )

        assertTrue(response.statusCode.is4xxClientError)

    }

    @Test
    @DisplayName("can get list of shifts without date range filter")
    fun canGetListOfShiftsForWorkerWithoutDateRange() {
        val shiftPayload = validShiftPayload(null, true).toMap()

        val response: ResponseEntity<com.nursify.api.model.ApiAvailableShifts> = restTemplate!!.postForEntity(
            generateURL(activeWorkerCNA),
            null,
            com.nursify.api.model.ApiAvailableShifts::class.java,
            shiftPayload
        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertThat(response.body?.content).isNotEmpty
        assertThat(response.body?.content?.map {
            assertThat(it.shifts).isNotEmpty
            val fields = it.javaClass.declaredFields
            val hasFieldOfTypeDate = fields.any { field ->
                field.type == LocalDate::class.java || LocalDate::class.java.isAssignableFrom(field.type)
            }
            assertThat(hasFieldOfTypeDate).isTrue()
        })

    }


    private fun validShiftPayload(facilityId: Long? = null, searchFromPast: Boolean): ApiShiftQueryPayload {

        return ApiShiftQueryPayload(
            facilityId,
            searchFromPast,
            sortObject.sort,
            sortObject.pageNumber,
            sortObject.pageSize,
        )
    }

    private fun shiftDatePayload(
        startDate: String = "2023-02-15",
        endDate: String = "2023-02-21"
    ): ApiShiftRangeRequestOffset {
        return ApiShiftRangeRequestOffset(
            startDate = stringToOffsetDateTime(startDate),
            endDate = stringToOffsetDateTime(endDate)
        )
    }

    private fun generateURL(workerId: Long): String {
        return "$apiUrl/shifts/${workerId}?facilityId={facilityId}&sort={sort}&pageSize={pageSize}&pageNumber={pageNumber}&searchFromPast={searchFromPast}"
    }

}
