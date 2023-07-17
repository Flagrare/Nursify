package com.nursify.repository

import clipboardhealth.challenge.nursify.config.utils.RepositoryTest
import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.enum.Profession
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


@DisplayName("[Worker] Repository Tests")
@RepositoryTest
internal class WorkerRepositoryTest {
    @Autowired
    private val repository: WorkerRepository? = null


    @Test
    @DisplayName("should save worker when successful")
    fun saveWorkerSuccess() {
        val workerToBeSaved = createWorker()
        val workerSaved = repository!!.save(workerToBeSaved)

        Assertions.assertThat(workerSaved).isNotNull
        Assertions.assertThat(workerSaved.id).isNotNull
        Assertions.assertThat(workerSaved.name).isEqualTo(workerToBeSaved.name)
    }

    private fun createWorker(id: Long? = 1, isActive: Boolean = false): Worker {
        return Worker(id, name = "test", profession = Profession.CNA, isActive = isActive)
    }

    @Test
    @DisplayName("should find worker on repository")
    fun findWorker() {
        val worker = repository?.findAll()?.first()
        Assertions.assertThat(worker?.name).isNotEmpty()
        assert(true)
    }

    @Test
    @DisplayName("should find worker by id when exists")
    fun findWorkerByIdExists() {
        val workerToBeSaved = createWorker()
        val savedWorker = repository!!.save(workerToBeSaved)
        val foundWorker = repository.findById(savedWorker.id!!)
        Assertions.assertThat(foundWorker).isPresent
        Assertions.assertThat(foundWorker.get()).isEqualTo(savedWorker)
    }

    @Test
    @DisplayName("should not find worker by id when not exists")
    fun findWorkerByIdNotExists() {
        val foundWorker = repository!!.findById(0)
        Assertions.assertThat(foundWorker).isNotPresent
    }

    @Test
    @DisplayName("should update worker")
    fun updateWorker() {
        val workerToBeSaved = createWorker()
        val savedWorker = repository!!.save(workerToBeSaved)
        val updatedWorker = savedWorker.copy(name = "Updated Worker")
        val workerAfterUpdate = repository.save(updatedWorker)
        Assertions.assertThat(workerAfterUpdate).isNotNull
        Assertions.assertThat(workerAfterUpdate.name).isEqualTo(updatedWorker.name)
    }

    @Test
    @DisplayName("should delete worker")
    fun deleteWorker() {
        val workerToBeSaved = createWorker()
        val savedWorker = repository!!.save(workerToBeSaved)
        repository.delete(savedWorker)
        val foundWorker = repository.findById(savedWorker.id!!)
        Assertions.assertThat(foundWorker).isNotPresent
    }


    @Test
    @DisplayName("should find worker by id and is active")
    fun findWorkerByIdAndIsActive() {
        val workerToBeSaved = createWorker(0, isActive = true)
        val savedWorker = repository!!.save(workerToBeSaved)

        val foundWorker = repository.findByIdAndIsActive(savedWorker.id!!, isActive = true)

        Assertions.assertThat(foundWorker).isNotNull
        Assertions.assertThat(foundWorker?.id).isEqualTo(savedWorker.id)
        Assertions.assertThat(foundWorker?.isActive).isEqualTo(true)
    }
}
