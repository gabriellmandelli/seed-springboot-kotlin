package com.greentower.seedApi.rest.exception

import org.springframework.security.core.AuthenticationException

class AuthUserNotFoundException : AuthenticationException {
    constructor( message: String) : super(message)
    constructor( message: String, throwable: Throwable) : super(message, throwable)
}