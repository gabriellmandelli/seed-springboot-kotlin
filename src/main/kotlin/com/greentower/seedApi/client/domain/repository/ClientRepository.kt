package com.greentower.seedApi.client.domain.repository

import com.greentower.seedApi.client.domain.entity.Client
import com.greentower.seedApi.infrastructure.generic.GenericRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : GenericRepository<Client>