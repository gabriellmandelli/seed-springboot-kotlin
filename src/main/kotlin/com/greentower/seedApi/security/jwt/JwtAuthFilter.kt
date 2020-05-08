package com.greentower.seedApi.security.jwt

import com.greentower.seedApi.service.AuthUserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthFilter(private var authUserService: AuthUserService, private var jwtService: JwtService) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {

            val token = authorizationHeader.split(" ").toTypedArray()[1]

            if (jwtService.isValidToken(token)) {
                val authUser = authUserService.loadUserByUsername(jwtService.getUseLoginFromToken(token))
                val credentials = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
                credentials.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = credentials
            }
        }

        filterChain.doFilter(request, response)

    }
}

