package com.nursify.repository

import clipboardhealth.challenge.nursify.config.utils.RepositoryTest
import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.entity.Shift
import clipboardhealth.challenge.nursify.entity.Worker
import clipboardhealth.challenge.nursify.enum.Profession
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant

@DisplayName("[Shift] Repository Tests")
@RepositoryTest
internal class ShiftRepositoryTest {
    @Autowired
    private lateinit var shiftRepository: ShiftRepository

    @Autowired
    private lateinit var facilityRepository: FacilityRepository

    @Autowired
    private lateinit var workerRepository: WorkerRepository

    private lateinit var facility: Facility
    private lateinit var worker: Worker

    @BeforeEach
    fun setup() {
        facility = createFacility()
        worker = createWorker()
    }

    @Test
    @DisplayName("should save shift when successful")
    fun saveShiftSuccess() {
        val shiftToBeSaved = createShift(facility)
        val shiftSaved = shiftRepository.save(shiftToBeSaved)

        Assertions.assertThat(shiftSaved).isNotNull
        Assertions.assertThat(shiftSaved.id).isNotNull
        Assertions.assertThat(shiftSaved.start).isEqualTo(shiftToBeSaved.start)
        Assertions.assertThat(shiftSaved.end).isEqualTo(shiftToBeSaved.end)
    }

    @Test
    @DisplayName("should find shift by id when exists")
    fun findShiftByIdExists() {
        val shiftToBeSaved = createShift(facility)
        val savedShift = shiftRepository.save(shiftToBeSaved)

        val foundShift = shiftRepository.findById(savedShift.id!!)
        Assertions.assertThat(foundShift).isPresent
        Assertions.assertThat(foundShift.get()).isEqualTo(savedShift)
    }

    @Test
    @DisplayName("should not find shift by id when not exists")
    fun findShiftByIdNotExists() {
        val foundShift = shiftRepository.findById(0)
        Assertions.assertThat(foundShift).isNotPresent
    }

    @Test
    @DisplayName("should update shift")
    fun updateShift() {
        val shiftToBeSaved = createShift(facility)
        val savedShift = shiftRepository.save(shiftToBeSaved)

        val updatedShift = savedShift.copy(profession = Profession.RN)
        val shiftAfterUpdate = shiftRepository.save(updatedShift)

        Assertions.assertThat(shiftAfterUpdate).isNotNull
        Assertions.assertThat(shiftAfterUpdate.profession).isEqualTo(updatedShift.profession)
    }

    @Test
    @DisplayName("should delete shift")
    fun deleteShift() {
        val shiftToBeSaved = createShift(facility)
        val savedShift = shiftRepository.save(shiftToBeSaved)

        shiftRepository.delete(savedShift)
        val foundShift = shiftRepository.findById(savedShift.id!!)
        Assertions.assertThat(foundShift).isNotPresent
    }

    private fun createFacility(): Facility {
        val facility = Facility(name = "Test Facility", isActive = true)
        return facilityRepository.save(facility)
    }

    private fun createShift(
        facility: Facility,
        profession: Profession = Profession.CNA,
        isClaimed: Boolean = false
    ): Shift {
        val shift = Shift(
            start = Instant.now(),
            end = Instant.now().plusSeconds(3600),
            profession = profession,
            facility = facility,
            worker = null
        )
        return if (isClaimed) {
            val worker = workerRepository.findAll().first()
            shift.copy(worker = worker)
        } else {
            shift
        }
    }

    private fun createWorker(): Worker {
        val worker = Worker(name = "Test Worker", profession = Profession.CNA)
        return workerRepository.save(worker)
    }
}
