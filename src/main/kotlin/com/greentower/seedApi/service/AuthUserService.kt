package com.greentower.seedApi.service

import com.greentower.seedApi.domain.entity.AuthUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

interface AuthUserService : UserDetailsService {

    fun save(authUser : AuthUser) : AuthUser

    fun update(authUser : AuthUser, id: UUID) : AuthUser

    fun findAll() : List<AuthUser>

    fun findById(id : UUID) : Optional<AuthUser>

    override fun loadUserByUsername(userName : String) : UserDetails

    fun deleteById(id : UUID)

    fun deleteAll()

    fun authenticate(authUser: AuthUser) : UserDetails
}