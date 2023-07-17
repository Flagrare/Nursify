package com.nursify.service

import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.entity.Shift
import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.entity.toGroupedShifts
import clipboardhealth.challenge.nursify.enum.Profession
import clipboardhealth.challenge.nursify.repository.ShiftRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.data.domain.PageImpl
import java.time.Instant
import java.time.OffsetDateTime

@DisplayName("[ShiftService] Unit Tests")
class ShiftServiceTest {

    private lateinit var shiftRepository: ShiftRepository
    private lateinit var workerService: WorkerService
    private lateinit var documentationService: DocumentationService
    private lateinit var shiftService: ShiftService

    @BeforeEach
    fun setup() {
        shiftRepository = mock(ShiftRepository::class.java)
        workerService = mock(WorkerService::class.java)
        documentationService = mock(DocumentationService::class.java)
        shiftService = ShiftService(shiftRepository, workerService, documentationService)
    }

    @Test
    @DisplayName("should get worker shifts")
    fun getWorkerShifts() {
        val workerId = 1L
        val facilityId = 2L
        val searchFromPast = false
        val pageNumber = 0
        val pageSize = 2
        val sort = listOf("start;asc")

        val startDate = OffsetDateTime.now().plusSeconds(3600)
        val endDate = OffsetDateTime.now().plusSeconds(7200)
        val apiWorkerShiftRange = com.nursify.api.model.ApiWorkerShiftRange(startDate = startDate, endDate = endDate)

        val worker = Worker(id = workerId, name = "Luke Skywalker", isActive = true, profession = Profession.CNA)
        val availableFacilities = listOf(Facility(id = facilityId, name = "Facility", isActive = true))
        val shifts = mutableListOf(
            Shift(
                id = 1L,
                start = Instant.now(),
                end = Instant.now(),
                profession = Profession.CNA,
                facility = Facility(id = 1, name = "Facility 1", isActive = true),
                worker = null
            ),
            Shift(
                id = 2L,
                start = Instant.now(),
                end = Instant.now(),
                profession = Profession.CNA,
                facility = Facility(id = 2, name = "Facility 1", isActive = true),
                worker = null
            )
        )
        val groupedShifts = shifts.toGroupedShifts()
        val pageRequest = com.nursify.common.buildPageRequest(sort, pageNumber, pageSize)
        `when`(workerService.getWorker(workerId)).thenReturn(worker)
        `when`(documentationService.getWorkerAvailableFacilities(worker, facilityId)).thenReturn(availableFacilities)
        `when`(
            shiftRepository.findAvailableShifts(
                availableFacilities,
                worker.profession,
                startDate.toInstant(),
                endDate.toInstant(),
                pageRequest
            )
        ).thenReturn(PageImpl(shifts))

        val expectedApiAvailableShifts = com.nursify.api.model.ApiAvailableShifts(
            pageNumber = pageNumber,
            pageSize = pageSize,
            content = groupedShifts,
            numberOfElements = shifts.size,
            totalPages = (shifts.size / pageSize) - (shifts.size % pageSize),
            totalElements = shifts.size.toLong(),
            isLast = true
        )

        val result = shiftService.getWorkerShifts(
            workerId = workerId,
            facilityId = facilityId,
            searchFromPast = searchFromPast,
            pageNumber = pageNumber,
            pageSize = pageSize,
            sort = sort,
            apiWorkerShiftRange = apiWorkerShiftRange,
        )
        assertThat(result).isEqualTo(expectedApiAvailableShifts)
    }
}
