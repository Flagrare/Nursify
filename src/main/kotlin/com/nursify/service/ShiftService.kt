package com.nursify.service

import clipboardhealth.challenge.nursify.dao.ApiShiftRequest
import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.entity.toGroupedShifts
import clipboardhealth.challenge.nursify.exception.BadRequestException
import clipboardhealth.challenge.nursify.exception.ExceptionTypesEnum
import clipboardhealth.challenge.nursify.exception.NotFoundException
import clipboardhealth.challenge.nursify.repository.ShiftRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class ShiftService(
    private val shiftRepository: ShiftRepository,
    private val workerService: WorkerService,
    private val documentationService: DocumentationService
) : com.nursify.api.ShiftsApiService {

    companion object {
        const val SORT_START_DATE_ASC = "start;asc"
    }

    override fun getWorkerShifts(
        workerId: Long,
        facilityId: Long?,
        searchFromPast: Boolean,
        pageNumber: Int,
        pageSize: Int,
        sort: List<String>?,
        apiWorkerShiftRange: com.nursify.api.model.ApiWorkerShiftRange?
    ): com.nursify.api.model.ApiAvailableShifts {

        val sortBy = sort ?: listOf(SORT_START_DATE_ASC)
        val pageRequest = com.nursify.common.buildPageRequest(sortBy, pageNumber, pageSize)

        val startDate = apiWorkerShiftRange?.startDate?.toInstant()
        val endDate = apiWorkerShiftRange?.endDate?.toInstant()

        validateShiftRange(startDate, endDate, searchFromPast)

        val worker = workerService.getWorker(workerId)
        val availableFacilities = documentationService.getWorkerAvailableFacilities(worker, facilityId)

        if (availableFacilities.isNotEmpty()) {

            val availableShifts =

                shiftRepository.findAvailableShifts(
                    availableFacilities,
                    worker.profession,
                    startDate = startDate,
                    endDate = endDate,
                    pageRequest = pageRequest
                )
            val groupedShiftsContent = availableShifts.content.toGroupedShifts()
            val apiAvailableShifts = com.nursify.api.model.ApiAvailableShifts(
                pageNumber = availableShifts.number,
                pageSize = availableShifts.size,
                numberOfElements = availableShifts.numberOfElements,
                totalPages = availableShifts.totalPages,
                totalElements = availableShifts.totalElements,
                isLast = availableShifts.isLast,
                content = groupedShiftsContent
            )
            return apiAvailableShifts
        }
        return com.nursify.api.model.ApiAvailableShifts(
            pageNumber = 0,
            pageSize = 0,
            numberOfElements = 0,
            totalPages = 0,
            totalElements = 0,
            isLast = true,
            content = emptyList()
        )
    }

    fun validateShiftRange(startDate: Instant?, endDate: Instant?, searchFromPast: Boolean) {
        startDate != null && endDate != null && startDate >= endDate && throw BadRequestException(
            type = ExceptionTypesEnum.PARAM_NOT_SUPPORTED,
            "Date range $startDate - $endDate is not valid."
        )
        !searchFromPast && startDate != null && startDate <= Instant.now(com.nursify.ClockHolder.CLOCK) && throw BadRequestException(
            type = ExceptionTypesEnum.PARAM_NOT_SUPPORTED,
            "Start date $startDate must be bigger than today's date.",
        )
    }

    fun claimShift(apiShiftRequest: ApiShiftRequest, worker: Worker) {
        val shift = shiftRepository.findByIdOrNull(apiShiftRequest.id) ?: throw NotFoundException(
            type = ExceptionTypesEnum.RESOURCE_NOT_FOUND,
            message = "Shift with id ${apiShiftRequest.id} not found."
        )

        val updatedShift = shift.copy(
            worker = worker
        )

        shiftRepository.save(updatedShift)
    }

}

