package com.greentower.seedApi.main.client.domain.exception

import com.greentower.seedApi.infrastructure.util.exception.ResponseStatusExceptionToLocate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ToDoResponseStatusMessage {
    companion object{
        fun getResponseNotFound() : ResponseStatusExceptionToLocate {
            return ResponseStatusExceptionToLocate(HttpStatus.NOT_FOUND, "entity.todo.not_found")
        }
    }
}