package com.greentower.seedApi.user.domain.entity

import com.greentower.seedApi.user.domain.enum.UserRole
import com.greentower.seedApi.user.domain.enum.UserStatus
import com.greentower.seedApi.infrastructure.generic.GenericEntity
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "auth_user")
class AuthUser : GenericEntity() {

    @Size(min = 1, max = 255, message = "Field name need be 1 between 255 carcters")
    @NotEmpty(message = "Field name is not empty")
    @NotNull(message = "Field name is not null")
    @Column(name = "name")
    var name = ""

    @Size(min = 1, max = 10, message = "Field username need be 1 between 10 carcters")
    @NotEmpty(message = "Field username is not empty")
    @NotNull(message = "Field username is not null")
    @Column(name = "username", unique = true)
    var username = ""

    @Size(min = 8, message = "Field password need be 8 carcters")
    @NotEmpty(message = "Field password is not empty")
    @NotNull(message = "Field password is not null")
    @Column(name = "password")
    var password = ""

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Field email is not empty")
    @NotNull(message = "Field email is not null")
    @Column(name = "email", unique = true)
    var email = ""

    @Column(name = "phone")
    var phone = ""

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    var status : UserStatus = UserStatus.DISABLED

    @Column(name = "roles")
    @Enumerated(value = EnumType.STRING)
    var role : UserRole = UserRole.ADMIN
}