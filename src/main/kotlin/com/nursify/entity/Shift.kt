package com.nursify.entity

import clipboardhealth.challenge.nursify.enum.Profession
import jakarta.persistence.*
import java.time.Instant
import java.time.ZoneOffset

@Entity(name = "Shift")
@Table(name = "`Shift`")
data class Shift(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "\"start\"")
    val start: Instant,
    @Column(name = "\"end\"")
    val end: Instant,
    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    val profession: Profession,
    @Column(name = "is_deleted")
    val isDeleted: Boolean = false,
    @JoinColumn(name = "facility_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val facility: Facility,
    @JoinColumn(name = "worker_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val worker: Worker? = null
) {
    val claimed: Boolean
        get() = worker != null
}

fun MutableList<Shift>.toGroupedShifts(): List<com.nursify.api.model.ApiGroupedShifts> {
    val apiShifts = this.map { it.toApi() }
    return apiShifts.groupBy { it.start.toLocalDate() }.map {
        com.nursify.api.model.ApiGroupedShifts(
            start = it.key,
            shifts = it.value
        )
    }
}


fun Shift.toApi(): com.nursify.api.model.ApiShift {
    return com.nursify.api.model.ApiShift(
        id = id!!,
        facility = facility.toApi(),
        end = end.atOffset(ZoneOffset.UTC),
        start = start.atOffset(ZoneOffset.UTC),
    )
}