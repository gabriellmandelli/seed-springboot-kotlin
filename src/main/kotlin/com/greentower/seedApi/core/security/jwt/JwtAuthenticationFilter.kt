package com.greentower.seedApi.core.security.jwt

import com.greentower.seedApi.rules.auth_user.service.AuthUserService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var authUserService: AuthUserService

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authorizationHeader != null && authorizationHeader.startsWith(jwtTokenProvider.TOKEN_PREFIX)) {
            val authToken = authorizationHeader.replace(jwtTokenProvider.TOKEN_PREFIX, "")
            this.checkToken(authToken, WebAuthenticationDetailsSource().buildDetails(request))
        } else {
            logger.error("Couldn't find bearer prefix. Header will be ignore.")
        }
        filterChain.doFilter(request, response)
    }

    private fun checkToken(authToken: String, authDetails: WebAuthenticationDetails){
        try {
            val username = jwtTokenProvider.getUsernameFromToken(authToken)

            if (username.isPresent && SecurityContextHolder.getContext().authentication == null){
                val customAuthUser = authUserService.loadUserByUsername(username.get())
                if (jwtTokenProvider.validateToken(authToken, customAuthUser)){
                    val authentication = jwtTokenProvider.getAuthentication(authToken, customAuthUser)
                    authentication.details = authDetails
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (exception : IllegalArgumentException){
            logger.error("An error occurred during while getting username from token.", exception)
        } catch (exception : ExpiredJwtException){
            logger.warn("The token is expired and not valid anymore.")
        } catch (exception : MalformedJwtException){
            logger.error("Authentication Failed. Unable to read JSON value.", exception)
        } catch (exception : SignatureException){
            logger.error("Authentication Failed. Username or Password not valid.", exception)
        }
    }
}
