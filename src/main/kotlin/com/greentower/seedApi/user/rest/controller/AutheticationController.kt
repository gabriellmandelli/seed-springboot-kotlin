package com.greentower.seedApi.user.rest.controller

import com.greentower.seedApi.infrastructure.security.jwt.JwtTokenProvider
import com.greentower.seedApi.user.domain.entity.AuthUser
import com.greentower.seedApi.user.rest.dto.AuthUserCredentialDTO
import com.greentower.seedApi.user.rest.dto.AuthUserTokenDTO
import com.greentower.seedApi.user.service.AuthUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AutheticationController(private var serviceAuthUser : AuthUserService, private var jwtTokenProvider: JwtTokenProvider) {

    @PostMapping("/user")
    fun authentication(@RequestBody credentialDTOAuth: AuthUserCredentialDTO) : ResponseEntity<AuthUserTokenDTO> {
        val authUser: AuthUser = AuthUser().apply {
            username = credentialDTOAuth.username
            password = credentialDTOAuth.password
        }

        val customAuthUser = serviceAuthUser.authenticate(authUser)

        return ResponseEntity.ok(AuthUserTokenDTO().apply {
            this.token = jwtTokenProvider.generateTokenByUserWithPrefix(customAuthUser)
            this.id = customAuthUser.id
        })
    }
}