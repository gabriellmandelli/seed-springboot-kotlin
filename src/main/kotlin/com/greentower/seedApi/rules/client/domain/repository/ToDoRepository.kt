package com.greentower.seedApi.rules.client.domain.repository

import com.greentower.seedApi.core.generic.GenericRepository
import com.greentower.seedApi.rules.client.domain.entity.ToDo
import org.springframework.stereotype.Repository

@Repository
interface ToDoRepository : GenericRepository<ToDo>