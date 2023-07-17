package com.nursify.service

import clipboardhealth.challenge.nursify.entity.DocumentWorker
import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.exception.ExceptionTypesEnum
import clipboardhealth.challenge.nursify.exception.NotFoundException
import clipboardhealth.challenge.nursify.repository.DocumentWorkerRepository
import clipboardhealth.challenge.nursify.repository.FacilityRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DocumentationService(
    private val documentWorkerRepository: DocumentWorkerRepository,
    private val facilityRepository: FacilityRepository,
) {
    @Cacheable("workerDocuments")
    fun getWorkerDocuments(worker: Worker): List<DocumentWorker?> {
        return documentWorkerRepository.findAllByWorker(worker).ifEmpty {
            throw NotFoundException(
                type = ExceptionTypesEnum.RESOURCE_NOT_FOUND,
                "No documents found for Worker ${worker.id}"
            )
        }
    }

    @Cacheable("facility")
    fun getFacility(facilityId: Long): Facility? {
        return facilityRepository.findByIdOrNull(facilityId)
    }


    @Cacheable("availableFacilities")
    fun getWorkerAvailableFacilities(worker: Worker, facilityId: Long?): List<Facility> {
        val workerDocuments = getWorkerDocuments(worker).map { it?.document?.id }
        val facility = facilityId?.let { getFacility(it) }

        return facilityRepository.findFacilitiesByWorkerDocumentIds(workerDocuments, facility)
    }
}