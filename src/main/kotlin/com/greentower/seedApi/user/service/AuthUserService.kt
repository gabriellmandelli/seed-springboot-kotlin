package com.greentower.seedApi.user.service

import com.greentower.seedApi.infrastructure.generic.GenericService
import com.greentower.seedApi.infrastructure.security.CustomAuthUser
import com.greentower.seedApi.user.domain.entity.AuthUser
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

interface AuthUserService : UserDetailsService, GenericService<AuthUser> {

    override fun save(entity : AuthUser) : AuthUser

    override fun update(entity : AuthUser, id: UUID) : AuthUser

    override fun findAll() : List<AuthUser>

    override fun findById(id : UUID) : Optional<AuthUser>

    override fun loadUserByUsername(userName : String) : CustomAuthUser

    override fun deleteById(id : UUID)

    override fun deleteAll()

    fun findByUserName(userName: String) : AuthUser

    fun authenticate(authUser: AuthUser) : CustomAuthUser

    fun updatePassword(username: String, oldPassword : String, newPassword: String): AuthUser
}