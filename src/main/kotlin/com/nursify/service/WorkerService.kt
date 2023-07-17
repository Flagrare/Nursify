package com.nursify.service

import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.exception.ExceptionTypesEnum
import clipboardhealth.challenge.nursify.exception.NotFoundException
import clipboardhealth.challenge.nursify.repository.WorkerRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class WorkerService(
    private val workerRepository: WorkerRepository
) {
    @Cacheable("worker")
    fun getWorker(workerId: Long, isActive: Boolean = true): Worker {
        return workerRepository.findByIdAndIsActive(workerId, isActive)
            ?: throw NotFoundException(
                type = ExceptionTypesEnum.RESOURCE_NOT_FOUND,
                "Worker with id $workerId not found!"
            )
    }
}