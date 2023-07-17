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

@DisplayName("[Facility] Repository Tests")
@RepositoryTest
class FacilityRepositoryTest {
    @Autowired
    private val facilityRepository: FacilityRepository? = null

    @Autowired
    private val documentRepository: DocumentRepository? = null

    @Autowired
    private val facilityRequirementRepository: FacilityRequirementRepository? = null

    private lateinit var facility1: Facility
    private lateinit var facility2: Facility
    private lateinit var document1: Document
    private lateinit var document2: Document

    @BeforeEach
    fun setup() {
        facility1 = Facility(name = "Facility 1", isActive = true)
        facility2 = Facility(name = "Facility 2", isActive = false)
        document1 = Document(name = "Document 1")
        document2 = Document(name = "Document 2")

        facility1 = facilityRepository!!.save(facility1)
        facility2 = facilityRepository.save(facility2)
        document1 = documentRepository!!.save(document1)
        document2 = documentRepository.save(document2)

        val facilityRequirement1 = FacilityRequirement(facility = facility1, document = document1)
        val facilityRequirement2 = FacilityRequirement(facility = facility2, document = document2)

        facilityRequirementRepository!!.saveAll(listOf(facilityRequirement1, facilityRequirement2))
    }

    @Test
    @DisplayName("should save facility when successful")
    fun saveFacilitySuccess() {
        val facilityToBeSaved = createFacility()
        val facilitySaved = facilityRepository!!.save(facilityToBeSaved)

        Assertions.assertThat(facilitySaved).isNotNull
        Assertions.assertThat(facilitySaved.id).isNotNull
        Assertions.assertThat(facilitySaved.name).isEqualTo(facilityToBeSaved.name)
    }

    @Test
    @DisplayName("should find facility by id when exists")
    fun findFacilityByIdExists() {
        val facilityToBeSaved = createFacility()
        val savedFacility = facilityRepository!!.save(facilityToBeSaved)

        val foundFacility = facilityRepository.findById(savedFacility.id!!)

        Assertions.assertThat(foundFacility).isPresent
        Assertions.assertThat(foundFacility.get()).isEqualTo(savedFacility)
    }

    @Test
    @DisplayName("should not find facility by id when not exists")
    fun findFacilityByIdNotExists() {
        val foundFacility = facilityRepository!!.findById(9999)

        Assertions.assertThat(foundFacility).isNotPresent
    }

    @Test
    @DisplayName("should update facility")
    fun updateFacility() {
        val facilityToBeSaved = createFacility()
        val savedFacility = facilityRepository!!.save(facilityToBeSaved)

        val updatedFacility = savedFacility.copy(name = "Updated Facility")
        val facilityAfterUpdate = facilityRepository.save(updatedFacility)

        Assertions.assertThat(facilityAfterUpdate).isNotNull
        Assertions.assertThat(facilityAfterUpdate.name).isEqualTo(updatedFacility.name)
    }

    @Test
    @DisplayName("should delete facility")
    fun deleteFacility() {
        val facilityToBeSaved = createFacility()
        val savedFacility = facilityRepository!!.save(facilityToBeSaved)

        facilityRepository.delete(savedFacility)

        val foundFacility = facilityRepository.findById(savedFacility.id!!)

        Assertions.assertThat(foundFacility).isNotPresent
    }

    @Test
    @DisplayName("should find facility by ID and isActive = true")
    fun findByIdAndIsActive_ExistsAndIsActive() {
        val foundFacility = facilityRepository!!.findByIdAndIsActive(facility1.id!!, true)
        Assertions.assertThat(foundFacility).isEqualTo(facility1)
    }

    @Test
    @DisplayName("should find facility by ID and isActive = true (facility not active)")
    fun findByIdAndIsActive_ExistsButNotIsActive() {
        val foundFacility = facilityRepository!!.findByIdAndIsActive(facility2.id!!, true)
        Assertions.assertThat(foundFacility).isNull()
    }

    @Test
    @DisplayName("should not find facility by ID and isActive = true (facility does not exist)")
    fun findByIdAndIsActive_NotExists() {
        val foundFacility = facilityRepository!!.findByIdAndIsActive(9999, true)
        Assertions.assertThat(foundFacility).isNull()
    }

    @Test
    @DisplayName("should find facilities by worker document IDs")
    fun findFacilitiesByWorkerDocumentIds() {
        val documentIds = listOf(document1.id, document2.id)
        val foundFacilities = facilityRepository!!.findFacilitiesByWorkerDocumentIds(documentIds)

        Assertions.assertThat(foundFacilities).containsExactly(facility1)
    }

    private fun createFacility(): Facility {
        return Facility(1, name = "test", isActive = true)
    }
}
