package com.greentower.seedApi.rules.ToDo.service

import com.greentower.seedApi.core.generic.GenericService
import com.greentower.seedApi.rules.client.domain.entity.ToDo
import java.util.*

interface ToDoService : GenericService<ToDo> {
    override fun save(entity: ToDo): ToDo

    override fun update(entity: ToDo, id: UUID): ToDo

    override fun findAll(): List<ToDo>

    override fun findById(id: UUID): Optional<ToDo>

    override fun deleteById(id: UUID)

    override fun deleteAll()
}