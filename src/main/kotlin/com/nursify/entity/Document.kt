package com.nursify.entity

import jakarta.persistence.*

@Entity(name = "Document")
@Table(name = "`Document`")
data class Document(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    val name: String,
)
