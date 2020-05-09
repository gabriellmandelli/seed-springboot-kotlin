package com.greentower.seedApi.util.generic

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

abstract class GenericController<T: GenericClass> {

    protected lateinit var service : GenericService<T, UUID>
    protected abstract var messageNotFound : String

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@Validated @RequestBody entity : T) : ResponseEntity<T> {
        return ResponseEntity.ok(service.save(entity))
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun update(@PathVariable("id") id : UUID, @Validated @RequestBody entity : T) : ResponseEntity<T> {
        return ResponseEntity.ok(service.update(entity, id))
    }

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id : UUID) : ResponseEntity<T> {
        return service.findById(id)
                .map { entity -> ResponseEntity.ok(entity) }
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, messageNotFound) }
    }

    @GetMapping
    fun findAll() : List<T>{
        return service.findAll()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    fun deleteById(@PathVariable("id") id : UUID){
        return service.deleteById(id)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    fun deleteAll(){
        return service.deleteAll()
    }
}