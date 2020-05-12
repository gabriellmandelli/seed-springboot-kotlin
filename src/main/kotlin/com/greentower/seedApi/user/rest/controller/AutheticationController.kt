package com.greentower.seedApi.user.rest.controller

import com.greentower.seedApi.infrastructure.security.jwt.JwtTokenProvider
import com.greentower.seedApi.user.domain.entity.AuthUser
import com.greentower.seedApi.user.rest.dto.AuthUserCredentialDTO
import com.greentower.seedApi.user.rest.dto.AuthUserTokenDTO
import com.greentower.seedApi.user.service.AuthUserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
class AutheticationController(private var serviceAuthUser : AuthUserService, private var jwtTokenProvider: JwtTokenProvider) {

    @PostMapping("/user")
    fun authentication(@RequestBody credentialDTOAuth: AuthUserCredentialDTO) : AuthUserTokenDTO? {
        return try {
            val authUser: AuthUser = AuthUser().apply {
                username = credentialDTOAuth.username
                password = credentialDTOAuth.password
            }

            val userDetails = serviceAuthUser.authenticate(authUser)
            val token = jwtTokenProvider.generateTokenByUser(userDetails)
            val user = serviceAuthUser.findByUserName(authUser.username)

            AuthUserTokenDTO().apply {
                this.username = userDetails.username
                this.token = "${jwtTokenProvider.getTypeAcessToken()} $token"
                this.id = user.id
            }

        } catch ( e : UsernameNotFoundException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        } catch ( e : RuntimeException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        }
    }
}