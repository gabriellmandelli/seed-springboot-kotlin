package com.greentower.seedApi.core.util

import com.greentower.seedApi.core.security.CustomAuthUser
import com.greentower.seedApi.rules.auth_user.domain.entity.AuthUser
import org.springframework.security.core.context.SecurityContextHolder

class AuthUserUtil{
    companion object{
        fun getCurrentAuthUser(): AuthUser {
            return AuthUser().apply {
                val customUser : CustomAuthUser
                SecurityContextHolder.getContext().authentication?.let {
                    customUser = it.principal as CustomAuthUser
                    this.id = customUser.id
                    this.username = customUser.username
                }
            }
        }
    }
}