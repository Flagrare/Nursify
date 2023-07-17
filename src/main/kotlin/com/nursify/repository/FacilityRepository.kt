package com.nursify.repository

import clipboardhealth.challenge.nursify.entity.Facility
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FacilityRepository : JpaRepository<Facility, Long> {


    fun findByIdAndIsActive(facilityId: Long, isActive: Boolean = true): Facility?

    @Query(
        """
    SELECT f
    FROM Facility f
    WHERE f.isActive = true
    AND (COALESCE(:facility, f) = f)
      AND NOT EXISTS (
          SELECT fr
          FROM FacilityRequirement fr
          WHERE fr.facility = f
            AND fr.document.id NOT IN :documentIds
      )
    """
    )
    fun findFacilitiesByWorkerDocumentIds(documentIds: List<Long?>, facility: Facility? = null): List<Facility>

}