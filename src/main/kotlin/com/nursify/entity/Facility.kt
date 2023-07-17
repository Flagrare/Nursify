package com.nursify.entity

import jakarta.persistence.*

@Entity(name = "Facility")
@Table(name = "`Facility`")
data class Facility(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    val name: String,
    @Column(name = "is_active")
    val isActive: Boolean = false,
)

fun Facility.toApi(): com.nursify.api.model.ApiFacility {
    return com.nursify.api.model.ApiFacility(
        id = id!!,
        name = name,
        isActive = isActive
    )
}