package com.greentower.seedApi.main.client.service

import com.greentower.seedApi.main.client.domain.entity.Client
import com.greentower.seedApi.infrastructure.generic.GenericService
import java.util.*

interface ClientService : GenericService<Client> {
    override fun save(entity: Client): Client

    override fun update(entity: Client, id: UUID): Client

    override fun findAll(): List<Client>

    override fun findById(id: UUID): Optional<Client>

    override fun deleteById(id: UUID)

    override fun deleteAll()
}