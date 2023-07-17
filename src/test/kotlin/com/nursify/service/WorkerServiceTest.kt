package com.nursify.service

import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.enum.Profession
import clipboardhealth.challenge.nursify.exception.ExceptionTypesEnum
import clipboardhealth.challenge.nursify.exception.NotFoundException
import clipboardhealth.challenge.nursify.repository.WorkerRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.*

@DisplayName("[WorkerService] Unit Tests")
internal class WorkerServiceTest {
    private val workerRepository: WorkerRepository = mock(WorkerRepository::class.java)
    private val workerService = WorkerService(workerRepository)

    @Test
    @DisplayName("should return worker when found")
    fun getWorkerFound() {
        val workerId = 1L
        val isActive = true
        val expectedWorker = Worker(workerId, "Naruto Uzumaki", isActive, Profession.CNA)
        `when`(workerRepository.findByIdAndIsActive(workerId, isActive)).thenReturn(expectedWorker)
        val result = workerService.getWorker(workerId, isActive)
        Assertions.assertThat(result).isEqualTo(expectedWorker)
    }

    @Test
    @DisplayName("should throw NotFoundException when worker not found")
    fun getWorkerNotFound() {
        val workerId = 1L
        val isActive = true
        `when`(workerRepository.findByIdAndIsActive(workerId, isActive)).thenReturn(null)
        Assertions.assertThatThrownBy { workerService.getWorker(workerId, isActive) }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessageContaining("Worker with id $workerId not found!")
            .matches { (it as NotFoundException).exceptionVO.type == ExceptionTypesEnum.RESOURCE_NOT_FOUND }

    }
}
