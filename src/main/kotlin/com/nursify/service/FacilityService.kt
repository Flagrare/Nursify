package com.nursify.service

import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.exception.ExceptionTypesEnum
import clipboardhealth.challenge.nursify.exception.NotFoundException
import clipboardhealth.challenge.nursify.repository.FacilityRepository
import org.springframework.stereotype.Service

@Service
class FacilityService(
    private val facilityRepository: FacilityRepository
) {
    fun getFacility(facilityId: Long, isActive: Boolean = true): Facility? {
        return facilityRepository.findByIdAndIsActive(facilityId, isActive)
            ?: throw NotFoundException(
                type = ExceptionTypesEnum.RESOURCE_NOT_FOUND,
                "Worker with id $facilityId not found!"
            )
    }
}