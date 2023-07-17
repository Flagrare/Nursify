package com.nursify.entity

import jakarta.persistence.*

@Entity(name = "FacilityRequirement")
@Table(name = "`FacilityRequirement`")
data class FacilityRequirement(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @JoinColumn(name = "facility_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val facility: Facility,
    @JoinColumn(name = "document_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val document: Document,
)
