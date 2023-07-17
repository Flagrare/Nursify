package com.nursify.service

import clipboardhealth.challenge.nursify.entity.Document
import clipboardhealth.challenge.nursify.entity.DocumentWorker
import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.enum.Profession
import clipboardhealth.challenge.nursify.exception.ExceptionTypesEnum
import clipboardhealth.challenge.nursify.exception.NotFoundException
import clipboardhealth.challenge.nursify.repository.DocumentWorkerRepository
import clipboardhealth.challenge.nursify.repository.FacilityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.*

@DisplayName("[DocumentationService] Unit Tests")
class DocumentationServiceTest {

    private lateinit var documentWorkerRepository: DocumentWorkerRepository
    private lateinit var facilityRepository: FacilityRepository
    private lateinit var documentationService: DocumentationService

    @BeforeEach
    fun setup() {
        documentWorkerRepository = mock(DocumentWorkerRepository::class.java)
        facilityRepository = mock(FacilityRepository::class.java)
        documentationService = DocumentationService(documentWorkerRepository, facilityRepository)
    }

    @Test
    @DisplayName("should throw NotFoundException when no documents found for worker")
    fun getWorkerAvailableFacilities_NoDocumentsFound() {
        val worker = Worker(id = 1L, name = "John Wick", isActive = true, profession = Profession.CNA)
        `when`(documentWorkerRepository.findAllByWorker(worker)).thenReturn(emptyList())

        val exception = org.junit.jupiter.api.Assertions.assertThrows(NotFoundException::class.java) {
            documentationService.getWorkerAvailableFacilities(worker, 1L)
        }

        assertThat(exception.exceptionVO.type).isEqualTo(ExceptionTypesEnum.RESOURCE_NOT_FOUND)
        assertThat(exception.message).isEqualTo("No documents found for Worker ${worker.id}")
    }

    @Test
    @DisplayName("should return facilities when worker and facilityId are valid")
    fun getWorkerAvailableFacilities_WorkerAndFacilityIdValid() {
        val worker = Worker(id = 1L, name = "Aragorn", isActive = true, profession = Profession.CNA)
        val document1 = Document(id = 1L, name = "Document 1")
        val document2 = Document(id = 2L, name = "Document 2")
        val documentWorker1 = DocumentWorker(id = 1L, worker = worker, document = document1)
        val documentWorker2 = DocumentWorker(id = 2L, worker = worker, document = document2)
        val facility = Facility(id = 1L, name = "Facility", isActive = true)

        `when`(documentWorkerRepository.findAllByWorker(worker)).thenReturn(listOf(documentWorker1, documentWorker2))
        `when`(facilityRepository.findById(1L)).thenReturn(Optional.ofNullable(facility))
        `when`(facilityRepository.findFacilitiesByWorkerDocumentIds(listOf(document1.id, document2.id), facility))
            .thenReturn(listOf(facility))

        val result = documentationService.getWorkerAvailableFacilities(worker, 1L)

        assertThat(result).containsExactly(facility)
    }
}
