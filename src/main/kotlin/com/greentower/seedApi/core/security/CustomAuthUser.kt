package com.greentower.seedApi.core.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*

class CustomAuthUser(var id: UUID?, username: String?, password: String?, authorities: MutableCollection<out GrantedAuthority>?) : User(username, password, authorities)