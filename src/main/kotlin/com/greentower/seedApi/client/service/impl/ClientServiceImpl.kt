package com.greentower.seedApi.client.service.impl

import com.greentower.seedApi.client.domain.entity.Client
import com.greentower.seedApi.client.service.ClientService
import com.greentower.seedApi.infrastructure.generic.GenericServiceImpl
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ClientServiceImpl : ClientService, GenericServiceImpl<Client>() {

}