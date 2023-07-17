package com.nursify.repository

import clipboardhealth.challenge.nursify.config.utils.RepositoryTest
import clipboardhealth.challenge.nursify.entity.Document
import clipboardhealth.challenge.nursify.entity.DocumentWorker
import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.enum.Profession
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("[DocumentWorker] Repository Tests")
@RepositoryTest
internal class DocumentWorkerRepositoryTest {
    @Autowired
    private lateinit var documentWorkerRepository: DocumentWorkerRepository

    @Autowired
    private lateinit var documentRepository: DocumentRepository

    @Autowired
    private lateinit var workerRepository: WorkerRepository

    private lateinit var document: Document
    private lateinit var worker: Worker

    @BeforeEach
    fun setup() {
        document = createDocument()
        worker = createWorker()
    }

    @Test
    @DisplayName("should save document worker when successful")
    fun saveDocumentWorkerSuccess() {
        val documentWorkerToBeSaved = DocumentWorker(1, worker, document)
        val documentWorkerSaved = documentWorkerRepository.save(documentWorkerToBeSaved)

        Assertions.assertThat(documentWorkerSaved).isNotNull
        Assertions.assertThat(documentWorkerSaved.id).isNotNull
        Assertions.assertThat(documentWorkerSaved.document).isEqualTo(document)
        Assertions.assertThat(documentWorkerSaved.worker).isEqualTo(worker)
    }

    @Test
    @DisplayName("should find document worker by id when exists")
    fun findDocumentWorkerByIdExists() {
        val documentWorkerToBeSaved = DocumentWorker(1, worker, document)
        val savedDocumentWorker = documentWorkerRepository.save(documentWorkerToBeSaved)

        val foundDocumentWorker = documentWorkerRepository.findById(savedDocumentWorker.id)
        Assertions.assertThat(foundDocumentWorker).isPresent
        Assertions.assertThat(foundDocumentWorker.get()).isEqualTo(savedDocumentWorker)
    }

    @Test
    @DisplayName("should not find document worker by id when not exists")
    fun findDocumentWorkerByIdNotExists() {
        val foundDocumentWorker = documentWorkerRepository.findById(9999)
        Assertions.assertThat(foundDocumentWorker).isNotPresent
    }

    @Test
    @DisplayName("should update document worker")
    fun updateDocumentWorker() {
        val documentWorkerToBeSaved = DocumentWorker(1, worker, document)
        val savedDocumentWorker = documentWorkerRepository.save(documentWorkerToBeSaved)

        val updatedDocumentWorker = savedDocumentWorker.copy(worker = createWorker())
        val documentWorkerAfterUpdate = documentWorkerRepository.save(updatedDocumentWorker)

        Assertions.assertThat(documentWorkerAfterUpdate).isNotNull
        Assertions.assertThat(documentWorkerAfterUpdate.worker).isEqualTo(updatedDocumentWorker.worker)
    }

    @Test
    @DisplayName("should delete document worker")
    fun deleteDocumentWorker() {
        val documentWorkerToBeSaved = DocumentWorker(1, worker, document)
        val savedDocumentWorker = documentWorkerRepository.save(documentWorkerToBeSaved)

        documentWorkerRepository.delete(savedDocumentWorker)
        val foundDocumentWorker = documentWorkerRepository.findById(savedDocumentWorker.id)
        Assertions.assertThat(foundDocumentWorker).isNotPresent
    }

    @Test
    @DisplayName("should find all document workers by worker")
    fun findAllDocumentsByWorker() {
        val documentWorkerToBeSaved = DocumentWorker(1, worker, document)
        documentWorkerRepository.save(documentWorkerToBeSaved)

        val foundDocumentWorkers = documentWorkerRepository.findAllByWorker(worker)
        Assertions.assertThat(foundDocumentWorkers).hasSize(1)
        Assertions.assertThat(foundDocumentWorkers[0]).isEqualTo(documentWorkerToBeSaved)
    }

    private fun createDocument(): Document {
        return documentRepository.save(Document(0, name = "test"))
    }

    private fun createWorker(): Worker {
        return workerRepository.save(Worker(0, name = "test", profession = Profession.CNA))
    }
}
