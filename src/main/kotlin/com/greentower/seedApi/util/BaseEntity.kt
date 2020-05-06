package com.greentower.seedApi.util

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
open class BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    var id: UUID? = null

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    var createdAt: Date? = null

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    var updateAt: Date? = null

}