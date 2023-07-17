package com.nursify.repository

import clipboardhealth.challenge.nursify.entity.Facility
import clipboardhealth.challenge.nursify.entity.Shift
import clipboardhealth.challenge.nursify.enum.Profession
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ShiftRepository : JpaRepository<Shift, Long> {
    @Query(
        """
    SELECT s FROM Shift s
    LEFT JOIN s.facility f
    WHERE s.worker IS NULL 
    AND s.facility IN (:facilities)
    AND s.isDeleted = false
    AND s.profession = :profession
    AND (COALESCE(:startDate, s.start) <= s.start AND COALESCE(:endDate, s.end) >= s.end)
"""
    )
    fun findAvailableShifts(
        @Param("facilities") facilities: List<Facility>,
        @Param("profession") profession: Profession,
        @Param("startDate") startDate: Instant?,
        @Param("endDate") endDate: Instant?,
        pageRequest: Pageable
    ): Page<Shift>

}