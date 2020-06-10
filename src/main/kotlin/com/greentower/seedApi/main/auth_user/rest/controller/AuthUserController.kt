package com.greentower.seedApi.main.auth_user.rest.controller

import com.greentower.seedApi.infrastructure.generic.GenericController
import com.greentower.seedApi.infrastructure.util.exception.ResponseStatusExceptionToLocate
import com.greentower.seedApi.main.auth_user.domain.entity.AuthUser
import com.greentower.seedApi.main.auth_user.domain.exception.AuthUserResponseStatusMessage
import com.greentower.seedApi.main.auth_user.rest.dto.AuthUserUpdatePasswordDTO
import com.greentower.seedApi.main.auth_user.service.AuthUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class AuthUserController: GenericController<AuthUser>() {

    @Autowired
    lateinit var serviceAuthUser: AuthUserService

    @Autowired
    fun setService(){
        this.service = serviceAuthUser
    }

    override fun getResponseNotFound(): ResponseStatusExceptionToLocate {
        return AuthUserResponseStatusMessage.getResponseNotFound()
    }

    @PutMapping("/password")
    fun updatePassword(@RequestBody passwordDTO : AuthUserUpdatePasswordDTO) : ResponseEntity<AuthUser> {
        return ResponseEntity.ok(
                this.serviceAuthUser.updatePassword(
                        username = passwordDTO.username,
                        oldPassword = passwordDTO.oldPassword,
                        newPassword = passwordDTO.newPassword))
    }
}