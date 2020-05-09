package com.greentower.seedApi.client.rest.controller

import com.greentower.seedApi.client.domain.entity.Client
import com.greentower.seedApi.client.service.ClientService
import com.greentower.seedApi.infrastructure.generic.GenericController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client")
class ClientController() : GenericController<Client>() {

    override var messageNotFound = "Client not found"

    @Autowired
    fun setService(service: ClientService){
        this.service = service
    }
}