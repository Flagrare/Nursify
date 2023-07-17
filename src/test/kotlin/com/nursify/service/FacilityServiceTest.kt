package com.nursify.service

import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.exception.NotFoundException
import clipboardhealth.challenge.nursify.repository.FacilityRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@DisplayName("[FacilityService] Unit Tests")
class FacilityServiceTest {

    private lateinit var facilityRepository: FacilityRepository
    private lateinit var facilityService: FacilityService

    @BeforeEach
    fun setup() {
        facilityRepository = mock(FacilityRepository::class.java)
        facilityService = FacilityService(facilityRepository)
    }

    @Test
    @DisplayName("should get facility by id")
    fun getFacility() {
        val facilityId = 1L
        val facility = Facility(id = facilityId, name = "Facility 1", isActive = true)
        `when`(facilityRepository.findByIdAndIsActive(facilityId, true)).thenReturn(facility)

        val result = facilityService.getFacility(facilityId, true)

        assertEquals(facility, result)
    }

    @Test
    @DisplayName("should throw NotFoundException when facility not found")
    fun getFacilityNotFound() {
        val facilityId = 1L
        `when`(facilityRepository.findByIdAndIsActive(facilityId, true)).thenReturn(null)

        assertThrows(NotFoundException::class.java) {
            facilityService.getFacility(facilityId, true)
        }
    }
}
