package com.greentower.seedApi.main.client.rest.controller

import com.greentower.seedApi.main.client.domain.entity.Client
import com.greentower.seedApi.main.client.domain.exception.ClientResponseStatusMessage
import com.greentower.seedApi.main.client.service.ClientService
import com.greentower.seedApi.infrastructure.generic.GenericController
import com.greentower.seedApi.infrastructure.util.exception.ResponseStatusExceptionToLocate
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