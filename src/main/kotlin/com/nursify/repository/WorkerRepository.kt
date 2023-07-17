package com.nursify.repository

import clipboardhealth.challenge.nursify.entity.Worker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkerRepository : JpaRepository<Worker, Long> {
    fun findByIdAndIsActive(workerId: Long, isActive: Boolean = true): Worker?
}
