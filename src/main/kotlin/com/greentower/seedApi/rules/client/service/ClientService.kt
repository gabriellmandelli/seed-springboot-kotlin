package com.greentower.seedApi.rules.client.service

import com.greentower.seedApi.rules.client.domain.entity.Client
import com.greentower.seedApi.core.generic.GenericService
import java.util.*

interface ClientService : GenericService<Client> {
    override fun save(entity: Client): Client

    override fun update(entity: Client, id: UUID): Client

    override fun findAll(): List<Client>

    override fun findById(id: UUID): Optional<Client>

    override fun deleteById(id: UUID)

    override fun deleteAll()
}