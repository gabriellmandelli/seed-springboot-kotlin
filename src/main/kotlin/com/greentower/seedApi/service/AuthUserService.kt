package com.greentower.seedApi.service

import com.greentower.seedApi.domain.entity.AuthUser
import com.greentower.seedApi.util.generic.GenericService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

interface AuthUserService : UserDetailsService, GenericService<AuthUser, UUID> {

    override fun save(entity : AuthUser) : AuthUser

    override fun update(entity : AuthUser, id: UUID) : AuthUser

    override fun findAll() : List<AuthUser>

    override fun findById(id : UUID) : Optional<AuthUser>

    override fun loadUserByUsername(userName : String) : UserDetails

    override fun deleteById(id : UUID)

    override fun deleteAll()

    fun authenticate(authUser: AuthUser) : UserDetails
}