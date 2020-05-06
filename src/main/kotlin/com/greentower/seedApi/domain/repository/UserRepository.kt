package com.greentower.seedApi.domain.repository

import com.greentower.seedApi.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID>{

    fun findByuserName(userName: String) : Optional<User>

}