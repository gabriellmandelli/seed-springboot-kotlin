package com.greentower.seedApi.rules.client.rest.controller

import com.greentower.seedApi.core.generic.GenericController
import com.greentower.seedApi.core.util.exception.ResponseStatusExceptionToLocate
import com.greentower.seedApi.rules.ToDo.service.ToDoService
import com.greentower.seedApi.rules.client.domain.entity.ToDo
import com.greentower.seedApi.rules.client.domain.exception.ToDoResponseStatusMessage
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