package com.greentower.seedApi.infrastructure.generic

import com.greentower.seedApi.infrastructure.util.exception.ResponseStatusExceptionToLocate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

abstract class GenericController<T: GenericClass> {

    protected lateinit var service : GenericService<T>

    open fun getResponseNotFound() : ResponseStatusExceptionToLocate{
        return ResponseStatusExceptionToLocate(HttpStatus.NOT_FOUND, "default.not_found")
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    open fun save(@Validated @RequestBody entity : T) : ResponseEntity<T> {
        return ResponseEntity.ok(service.save(entity))
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    open fun update(@PathVariable("id") id : UUID, @Validated @RequestBody entity : T) : ResponseEntity<T> {
        return ResponseEntity.ok(service.update(entity, id))
    }

    @GetMapping("{id}")
    open fun findById(@PathVariable("id") id : UUID) : ResponseEntity<T> {
        return service.findById(id)
                .map { entity -> ResponseEntity.ok(entity) }
                .orElseThrow { getResponseNotFound() }
    }

    @GetMapping
    open fun findAll() : List<T>{
        return service.findAll()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    open fun deleteById(@PathVariable("id") id : UUID){
        return service.deleteById(id)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    open fun deleteAll(){
        return service.deleteAll()
    }
}