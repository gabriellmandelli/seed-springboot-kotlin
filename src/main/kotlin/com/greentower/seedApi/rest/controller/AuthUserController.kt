package com.greentower.seedApi.rest.controller

import com.greentower.seedApi.domain.entity.AuthUser
import com.greentower.seedApi.security.jwt.JwtService
import com.greentower.seedApi.service.AuthUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/user")
class AuthUserController(private var serviceAuth : AuthUserService, private var jwtService: JwtService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@Validated @RequestBody authUser : AuthUser) : ResponseEntity<AuthUser> {
        return ResponseEntity.ok(serviceAuth.save(authUser))
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun update(@PathVariable("id") id : UUID, @Validated @RequestBody authUser : AuthUser) : ResponseEntity<AuthUser> {
        return ResponseEntity.ok(serviceAuth.update(authUser, id))
    }

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id : UUID) : ResponseEntity<AuthUser> {
        return serviceAuth.findById(id)
                .map { user -> ResponseEntity.ok(user) }
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User id: $id not found") }
    }

    @GetMapping
    fun findAll() : List<AuthUser>{
        return serviceAuth.findAll()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    fun deleteById(@PathVariable("id") id : UUID){
        return serviceAuth.deleteById(id)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    fun deleteAll(){
        return serviceAuth.deleteAll()
    }
}