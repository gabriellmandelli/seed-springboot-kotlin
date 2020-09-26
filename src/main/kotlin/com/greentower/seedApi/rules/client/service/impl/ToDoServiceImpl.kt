package com.greentower.seedApi.rules.client.service.impl

import com.greentower.seedApi.core.generic.GenericServiceImpl
import com.greentower.seedApi.rules.ToDo.service.ToDoService
import com.greentower.seedApi.rules.client.domain.entity.ToDo
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ToDoServiceImpl : ToDoService, GenericServiceImpl<ToDo>()