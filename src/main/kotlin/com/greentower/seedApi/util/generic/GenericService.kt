package com.greentower.seedApi.util.generic

import java.util.*

interface GenericService<T, ID> {
    fun save(entity : T) : T

    fun update(entity : T, id: ID) : T

    fun findAll() : List<T>

    fun findById(id : ID) : Optional<T>

    fun deleteById(id : ID)

    fun deleteAll()
}