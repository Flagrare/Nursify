package com.nursify.repository

import clipboardhealth.challenge.nursify.config.utils.RepositoryTest
import clipboardhealth.challenge.nursify.entity.Document
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("[Document] Repository Tests")
@RepositoryTest
internal class DocumentRepositoryTest {
    @Autowired
    private lateinit var repository: DocumentRepository

    @Test
    @DisplayName("should save document when successful")
    fun saveDocumentSuccess() {
        val documentToBeSaved = createDocument()
        val documentSaved = repository.save(documentToBeSaved)

        Assertions.assertThat(documentSaved).isNotNull
        Assertions.assertThat(documentSaved.id).isNotNull
        Assertions.assertThat(documentSaved.name).isEqualTo(documentToBeSaved.name)
    }

    @Test
    @DisplayName("should find document by id when exists")
    fun findDocumentByIdExists() {
        val documentToBeSaved = createDocument()
        val savedDocument = repository.save(documentToBeSaved)
        val foundDocument = repository.findById(savedDocument.id!!)
        Assertions.assertThat(foundDocument).isPresent
        Assertions.assertThat(foundDocument.get()).isEqualTo(savedDocument)
    }

    @Test
    @DisplayName("should not find document by id when not exists")
    fun findDocumentByIdNotExists() {
        val foundDocument = repository.findById(9999)
        Assertions.assertThat(foundDocument).isNotPresent
    }

    @Test
    @DisplayName("should update document")
    fun updateDocument() {
        val documentToBeSaved = createDocument()
        val savedDocument = repository.save(documentToBeSaved)
        val updatedDocument = savedDocument.copy(name = "Updated Document")
        val documentAfterUpdate = repository.save(updatedDocument)
        Assertions.assertThat(documentAfterUpdate).isNotNull
        Assertions.assertThat(documentAfterUpdate.name).isEqualTo(updatedDocument.name)
    }

    @Test
    @DisplayName("should delete document")
    fun deleteDocument() {
        val documentToBeSaved = createDocument()
        val savedDocument = repository.save(documentToBeSaved)
        repository.delete(savedDocument)
        val foundDocument = repository.findById(savedDocument.id!!)
        Assertions.assertThat(foundDocument).isNotPresent
    }

    private fun createDocument(): Document {
        return Document(1, name = "test")
    }
}
