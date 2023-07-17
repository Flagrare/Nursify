package com.nursify.repository

import clipboardhealth.challenge.nursify.entity.DocumentWorker
import clipboardhealth.challenge.nursify.entity.Worker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentWorkerRepository : JpaRepository<DocumentWorker, Long> {
    fun findAllByWorker(worker: Worker): List<DocumentWorker?>
}