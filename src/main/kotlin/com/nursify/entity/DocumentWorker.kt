package com.nursify.entity

import jakarta.persistence.*

@Entity(name = "DocumentWorker")
@Table(name = "`DocumentWorker`")
data class DocumentWorker(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @JoinColumn(name = "worker_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val worker: Worker,
    @JoinColumn(name = "document_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val document: Document
)
