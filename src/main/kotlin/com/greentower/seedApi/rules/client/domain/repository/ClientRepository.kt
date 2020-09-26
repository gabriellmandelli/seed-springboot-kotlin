package com.greentower.seedApi.rules.client.domain.repository

import com.greentower.seedApi.rules.client.domain.entity.Client
import com.greentower.seedApi.core.generic.GenericRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : GenericRepository<Client>