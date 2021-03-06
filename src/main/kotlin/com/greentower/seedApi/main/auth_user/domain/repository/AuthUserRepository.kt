package com.greentower.seedApi.main.auth_user.domain.repository

import com.greentower.seedApi.infrastructure.generic.GenericRepository
import com.greentower.seedApi.main.auth_user.domain.entity.AuthUser
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthUserRepository<T> : GenericRepository<AuthUser> {
    fun findByusername(userName: String) : Optional<AuthUser>
}