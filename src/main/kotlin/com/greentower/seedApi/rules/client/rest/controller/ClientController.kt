package com.greentower.seedApi.rules.client.rest.controller

import com.greentower.seedApi.rules.client.domain.entity.Client
import com.greentower.seedApi.rules.client.domain.exception.ClientResponseStatusMessage
import com.greentower.seedApi.rules.client.service.ClientService
import com.greentower.seedApi.core.generic.GenericController
import com.greentower.seedApi.core.util.exception.ResponseStatusExceptionToLocate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client")
class ClientController() : GenericController<Client>() {

    override fun getResponseNotFound(): ResponseStatusExceptionToLocate {
        return ClientResponseStatusMessage.getResponseNotFound()
    }

    @Autowired
    fun setService(service: ClientService){
        this.service = service
    }
}