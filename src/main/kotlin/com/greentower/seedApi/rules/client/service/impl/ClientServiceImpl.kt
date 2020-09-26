package com.greentower.seedApi.rules.client.service.impl

import com.greentower.seedApi.rules.client.domain.entity.Client
import com.greentower.seedApi.rules.client.service.ClientService
import com.greentower.seedApi.core.generic.GenericServiceImpl
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ClientServiceImpl : ClientService, GenericServiceImpl<Client>()