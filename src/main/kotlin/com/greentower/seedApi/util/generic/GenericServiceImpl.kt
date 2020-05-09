package com.greentower.seedApi.util.generic

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.transaction.Transactional

abstract class GenericServiceImpl<T : GenericClass, ID> : GenericService<T, ID> {

    @Autowired
    lateinit var repository : JpaRepository<T, ID>

    @Transactional
    override fun save(entity: T): T {
        return repository.save(entity)
    }

    @Transactional
    override fun update(entity: T, id : ID): T {
        return repository.save(entity)
    }

    override fun findAll(): List<T> {
        return repository.findAll()
    }

    override fun findById(id: ID): Optional<T> {
        return repository.findById(id)
    }

    @Transactional
    override fun deleteById(id: ID) {
        return repository.deleteById(id)
    }

    @Transactional
    override fun deleteAll() {
        return repository.deleteAll()
    }
}