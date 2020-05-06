package com.greentower.seedApi.service

import com.greentower.seedApi.domain.entity.User
import java.util.*

interface UserService {

    fun save(user : User) : User

    fun update(user : User, id: UUID) : User

    fun findAll() : List<User>

    fun findById(id : UUID) : Optional<User>

    fun findByUserName(userName : String) : Optional<User>

    fun deleteById(id : UUID)

    fun deleteAll()

}