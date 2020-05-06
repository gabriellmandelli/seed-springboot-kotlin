package com.greentower.seedApi.domain.entity

import com.greentower.seedApi.util.BaseEntity
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(name = "auth_user")
class User : BaseEntity() {

    @Size(min = 1, max = 255, message = "Field name need be 1 between 255 carcters")
    @NotEmpty(message = "Field name is not empty")
    @NotNull(message = "Field name is not null")
    @Column(name = "name")
    var name = ""

    @Size(min = 1, max = 10, message = "Field userName need be 1 between 10 carcters")
    @NotEmpty(message = "Field userName is not empty")
    @NotNull(message = "Field userName is not null")
    @Column(name = "userName")
    var userName = ""

    @Size(min = 8, message = "Field password need be 8 carcters")
    @NotEmpty(message = "Field password is not empty")
    @NotNull(message = "Field password is not null")
    @Column(name = "password")
    var password = ""

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Field email is not empty")
    @NotNull(message = "Field email is not null")
    @Column(name = "email")
    var email = ""

    @Column(name = "phone")
    var phone = ""

    @Column(name = "status")
    var status = ""
}