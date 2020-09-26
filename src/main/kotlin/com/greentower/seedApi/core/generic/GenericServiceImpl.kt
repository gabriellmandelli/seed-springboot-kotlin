package com.greentower.seedApi.core.generic

import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.transaction.Transactional

abstract class GenericServiceImpl<T : GenericClass> : GenericService<T> {

    @Autowired
    lateinit var repository : GenericRepository<T>

    @Transactional
    override fun save(entity: T): T {
        return repository.save(entity)
    }

    @Transactional
    override fun update(entity: T, id : UUID): T {
        return repository.save(entity)
    }

    override fun findAll(): List<T> {
        return repository.findAll()
    }

    override fun findById(id: UUID): Optional<T> {
        return repository.findById(id)
    }

    @Transactional
    override fun deleteById(id: UUID) {
        return repository.deleteById(id)
    }

    @Transactional
    override fun deleteAll() {
        return repository.deleteAll()
    }
}