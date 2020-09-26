package com.greentower.seedApi.core.generic

import java.util.*

interface GenericService<T : GenericClass> {

    fun save(entity : T) : T

    fun update(entity : T, id: UUID) : T

    fun findAll() : List<T>

    fun findById(id : UUID) : Optional<T>

    fun deleteById(id : UUID)

    fun deleteAll()
}