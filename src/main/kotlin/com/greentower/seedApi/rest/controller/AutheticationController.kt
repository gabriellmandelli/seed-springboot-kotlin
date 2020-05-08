package com.greentower.seedApi.rest.controller

import com.greentower.seedApi.domain.entity.AuthUser
import com.greentower.seedApi.rest.dto.AuthUserCredentialDTO
import com.greentower.seedApi.rest.dto.AuthUserTokenDTO
import com.greentower.seedApi.security.jwt.JwtService
import com.greentower.seedApi.service.AuthUserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
class AutheticationController(private var serviceAuthUser : AuthUserService, private var jwtService: JwtService) {

    @PostMapping("/user")
    fun authentication(@RequestBody credentialDTOAuth: AuthUserCredentialDTO) : AuthUserTokenDTO? {
        return try {
            val authUser: AuthUser = AuthUser().apply {
                username = credentialDTOAuth.username
                password = credentialDTOAuth.password
            }

            val userDetails = serviceAuthUser.authenticate(authUser)
            val token = jwtService.generateTokenByUser(userDetails)

            AuthUserTokenDTO().apply {
                this.username = credentialDTOAuth.username
                this.token = token
            }

        } catch ( e : UsernameNotFoundException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        } catch ( e : RuntimeException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        }
    }
}