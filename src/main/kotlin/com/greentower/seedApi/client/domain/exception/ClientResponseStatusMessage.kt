package com.greentower.seedApi.client.domain.exception

import com.greentower.seedApi.infrastructure.util.exception.ResponseStatusExceptionToLocate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ClientResponseStatusMessage {
    companion object{
        fun getResponseNotFound() : ResponseStatusExceptionToLocate {
            return ResponseStatusExceptionToLocate(HttpStatus.NOT_FOUND, "entity.client.not_found")
        }
    }
}