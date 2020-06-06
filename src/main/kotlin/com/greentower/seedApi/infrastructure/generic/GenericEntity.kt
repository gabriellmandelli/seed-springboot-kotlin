package com.greentower.seedApi.infrastructure.generic

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class GenericEntity: GenericClass {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    var id: UUID? = null

    @CreationTimestamp
    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    @JsonIgnore
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updateAt", nullable = false)
    @UpdateTimestamp
    @JsonIgnore
    var updateAt: LocalDateTime? = null

    @Column(name = "sequential", columnDefinition="serial")
    @Generated(GenerationTime.INSERT)
    @JsonIgnore
    var sequential = 0
}