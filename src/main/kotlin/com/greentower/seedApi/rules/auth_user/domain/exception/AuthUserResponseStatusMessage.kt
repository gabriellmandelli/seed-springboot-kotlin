package com.greentower.seedApi.rules.auth_user.domain.exception

import com.greentower.seedApi.core.util.exception.ResponseStatusExceptionToLocate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AuthUserResponseStatusMessage{
    companion object {
        fun getResponseNotFound() : ResponseStatusExceptionToLocate{
            return ResponseStatusExceptionToLocate(HttpStatus.NOT_FOUND, "entity.auth_user.not_found")
        }

        fun getResponsePasswordNotMatches() : ResponseStatusExceptionToLocate{
            return ResponseStatusExceptionToLocate(HttpStatus.UNAUTHORIZED, "entity.auth_user.password_not_matches")
        }
    }
}