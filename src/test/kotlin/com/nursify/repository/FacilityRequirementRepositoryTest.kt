package com.nursify.repository

import clipboardhealth.challenge.nursify.config.utils.RepositoryTest
import clipboardhealth.challenge.nursify.entity.Document
import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.entity.FacilityRequirement
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("[FacilityRequirement] Repository Tests")
@RepositoryTest
internal class FacilityRequirementRepositoryTest {
    @Autowired
    private lateinit var facilityRequirementRepository: FacilityRequirementRepository

    @Autowired
    private lateinit var facilityRepository: FacilityRepository

    @Autowired
    private lateinit var documentRepository: DocumentRepository

    private lateinit var facility: Facility
    private lateinit var document: Document

    @BeforeEach
    fun setup() {
        facility = createFacility()
        document = createDocument()
    }

    @Test
    @DisplayName("should save facility requirement when successful")
    fun saveFacilityRequirementSuccess() {
        val facilityRequirementToBeSaved = FacilityRequirement(1, facility, document)
        val facilityRequirementSaved = facilityRequirementRepository.save(facilityRequirementToBeSaved)

        Assertions.assertThat(facilityRequirementSaved).isNotNull
        Assertions.assertThat(facilityRequirementSaved.id).isNotNull
        Assertions.assertThat(facilityRequirementSaved.facility).isEqualTo(facility)
        Assertions.assertThat(facilityRequirementSaved.document).isEqualTo(document)
    }

    @Test
    @DisplayName("should find facility requirement by id when exists")
    fun findFacilityRequirementByIdExists() {
        val facilityRequirementToBeSaved = FacilityRequirement(1, facility, document)
        val savedFacilityRequirement = facilityRequirementRepository.save(facilityRequirementToBeSaved)

        val foundFacilityRequirement = facilityRequirementRepository.findById(savedFacilityRequirement.id!!)
        Assertions.assertThat(foundFacilityRequirement).isPresent
        Assertions.assertThat(foundFacilityRequirement.get()).isEqualTo(savedFacilityRequirement)
    }

    @Test
    @DisplayName("should not find facility requirement by id when not exists")
    fun findFacilityRequirementByIdNotExists() {
        val foundFacilityRequirement = facilityRequirementRepository.findById(9999)
        Assertions.assertThat(foundFacilityRequirement).isNotPresent
    }

    @Test
    @DisplayName("should update facility requirement")
    fun updateFacilityRequirement() {
        val facilityRequirementToBeSaved = FacilityRequirement(1, facility, document)
        val savedFacilityRequirement = facilityRequirementRepository.save(facilityRequirementToBeSaved)

        val updatedFacilityRequirement = savedFacilityRequirement.copy(document = createDocument())
        val facilityRequirementAfterUpdate = facilityRequirementRepository.save(updatedFacilityRequirement)

        Assertions.assertThat(facilityRequirementAfterUpdate).isNotNull
        Assertions.assertThat(facilityRequirementAfterUpdate.document).isEqualTo(updatedFacilityRequirement.document)
    }

    @Test
    @DisplayName("should delete facility requirement")
    fun deleteFacilityRequirement() {
        val facilityRequirementToBeSaved = FacilityRequirement(1, facility, document)
        val savedFacilityRequirement = facilityRequirementRepository.save(facilityRequirementToBeSaved)

        facilityRequirementRepository.delete(savedFacilityRequirement)
        val foundFacilityRequirement = facilityRequirementRepository.findById(savedFacilityRequirement.id!!)
        Assertions.assertThat(foundFacilityRequirement).isNotPresent
    }

    private fun createFacility(): Facility {
        return facilityRepository.save(Facility(1, name = "test", isActive = true))
    }

    private fun createDocument(): Document {
        return documentRepository.save(Document(1, name = "test"))
    }
}
