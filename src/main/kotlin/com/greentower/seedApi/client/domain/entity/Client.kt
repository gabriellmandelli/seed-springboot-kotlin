package com.greentower.seedApi.client.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.greentower.seedApi.client.domain.enum.ClientStatus
import com.greentower.seedApi.infrastructure.generic.GenericEntity
import com.greentower.seedApi.user.domain.entity.AuthUser
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "client")
class Client : GenericEntity() {

    @Size(min = 1, max = 255, message = "Field name need be 1 between 255 carcters")
    @NotEmpty(message = "Field name is not empty")
    @NotNull(message = "Field name is not null")
    @Column(name = "name")
    var name = ""

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Field email is not empty")
    @NotNull(message = "Field email is not null")
    @Column(name = "email", unique = true)
    var email = ""

    @Column(name = "phone")
    var phone = ""

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    var status : ClientStatus = ClientStatus.DISABLED

    @ManyToOne
    @JoinColumn(name = "auth_user")
    var user : AuthUser? = null
}