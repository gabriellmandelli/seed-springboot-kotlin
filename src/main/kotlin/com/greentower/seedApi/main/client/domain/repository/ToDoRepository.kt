package com.greentower.seedApi.main.client.domain.repository

import com.greentower.seedApi.infrastructure.generic.GenericRepository
import com.greentower.seedApi.main.client.domain.entity.ToDo
import org.springframework.stereotype.Repository

@Repository
interface ToDoRepository : GenericRepository<ToDo>