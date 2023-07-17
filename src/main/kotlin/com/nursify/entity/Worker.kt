package com.nursify.entity

import clipboardhealth.challenge.nursify.enum.Profession
import jakarta.persistence.*

@Entity(name = "Worker")
@Table(name = "`Worker`")
data class Worker(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    val name: String,
    @Column(name = "is_active")
    val isActive: Boolean = false,
    @Enumerated(EnumType.STRING)
    @Column(name = "profession")
    val profession: Profession
)
