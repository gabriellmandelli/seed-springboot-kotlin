package com.greentower.seedApi.main.client.rest.controller

import com.greentower.seedApi.infrastructure.generic.GenericController
import com.greentower.seedApi.infrastructure.util.exception.ResponseStatusExceptionToLocate
import com.greentower.seedApi.main.ToDo.service.ToDoService
import com.greentower.seedApi.main.client.domain.entity.ToDo
import com.greentower.seedApi.main.client.domain.exception.ToDoResponseStatusMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todo")
class ToDoController() : GenericController<ToDo>() {

    override fun getResponseNotFound(): ResponseStatusExceptionToLocate {
        return ToDoResponseStatusMessage.getResponseNotFound()
    }

    @Autowired
    fun setService(service: ToDoService){
        this.service = service
    }
}