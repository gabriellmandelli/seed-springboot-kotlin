package com.greentower.seedApi.rest.controller

import com.greentower.seedApi.domain.entity.User
import com.greentower.seedApi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(private var service : UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@Validated @RequestBody user : User) : User {
        return service.save(user)
    }

    @PutMapping(value = ["{id}"])
    fun update(@PathVariable("id") id : UUID, @Validated @RequestBody user : User) : User {
        return service.update(user, id)
    }

    @GetMapping(value = ["{id}"])
    fun findById(@PathVariable("id") id : UUID) : User {
        return service.findById(id)
                .map { user -> user }
                .orElseThrow {ResponseStatusException(HttpStatus.NOT_FOUND, "User id:$id not found")}
    }

    @GetMapping
    fun findAll() : List<User>{
        return service.findAll()
    }

    @DeleteMapping(value = ["{id}"])
    fun deleteById(@PathVariable("id") id : UUID){
        return service.deleteById(id)
    }

    @DeleteMapping
    fun deleteAll(){
        return service.deleteAll()
    }
}