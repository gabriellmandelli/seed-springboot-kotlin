package com.greentower.seedApi.infrastructure.util.exception

import com.greentower.seedApi.infrastructure.util.TranslatorUtil
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ResponseStatusExceptionToLocate : ResponseStatusException{
    constructor(status: HttpStatus, reason: String, throwable: Throwable) : super(status, TranslatorUtil.toLocale(reason), throwable)
    constructor(status: HttpStatus, reason: String) : super(status, TranslatorUtil.toLocale(reason), null)
}