package com.greentower.seedApi.rest.controller

import com.greentower.seedApi.domain.entity.AuthUser
import com.greentower.seedApi.service.AuthUserService
import com.greentower.seedApi.util.generic.GenericController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class AuthUserController: GenericController<AuthUser>() {

    override var messageNotFound = "User not found"

    @Autowired
    fun setService(service: AuthUserService){
        this.service = service
    }
}