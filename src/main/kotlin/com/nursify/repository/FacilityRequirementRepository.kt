package com.nursify.repository

import clipboardhealth.challenge.nursify.entity.FacilityRequirement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FacilityRequirementRepository : JpaRepository<FacilityRequirement, Long>