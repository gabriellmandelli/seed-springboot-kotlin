package com.greentower.seedApi.main.client.service.impl

import com.greentower.seedApi.infrastructure.generic.GenericServiceImpl
import com.greentower.seedApi.main.ToDo.service.ToDoService
import com.greentower.seedApi.main.client.domain.entity.ToDo
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ToDoServiceImpl : ToDoService, GenericServiceImpl<ToDo>()