package com.greentower.seedApi.domain.repository

import com.greentower.seedApi.domain.entity.AuthUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthUserRepository : JpaRepository<AuthUser, UUID> {
    fun findByusername(userName: String) : Optional<AuthUser>
}