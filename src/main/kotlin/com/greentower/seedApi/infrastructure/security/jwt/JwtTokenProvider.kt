package com.greentower.seedApi.infrastructure.security.jwt

import com.greentower.seedApi.infrastructure.config.JwtConfig
import com.greentower.seedApi.infrastructure.security.CustomAuthUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtTokenProvider() {

    @Autowired
    private lateinit var jwtConfig: JwtConfig

    private val AUTHORITIES_KEY = "scopes"

    val TOKEN_PREFIX = "Bearer "

    private fun generateTokenByUser(customAuthUser: CustomAuthUser) : String {
        val expirationDate : Date = Date.from(LocalDateTime.now().plusMinutes(jwtConfig.expirationTimeMinutes.toLong()).atZone(ZoneId.systemDefault()).toInstant())

        return Jwts.builder()
                .setSubject(customAuthUser.username)
                .claim(AUTHORITIES_KEY, customAuthUser.authorities)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.signingKey)
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(expirationDate)
                .compact()
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(jwtConfig.signingKey)
                .parseClaimsJws(token)
                .body
    }

    private fun isTokenExpired(authToken : String) : Boolean {
        return try {
            LocalDateTime.now().isAfter(getAllClaimsFromToken(authToken).expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        }catch (exception : Exception){
            true
        }
    }

    fun getUsernameFromToken(token: String) : Optional<String> {
        return Optional.ofNullable(getAllClaimsFromToken(token).subject)
    }

    fun getAuthentication(authToken : String, customAuthUser : CustomAuthUser) : UsernamePasswordAuthenticationToken{
        return UsernamePasswordAuthenticationToken(customAuthUser, "", customAuthUser.authorities)
    }

    fun generateTokenByUserWithPrefix(customAuthUser: CustomAuthUser) : String{
        return TOKEN_PREFIX.plus(this.generateTokenByUser(customAuthUser))
    }

    fun validateToken(authToken: String, customAuthUser : CustomAuthUser) : Boolean{
        return this.getUsernameFromToken(authToken)
                .map { username -> username.equals(customAuthUser.username) && !isTokenExpired(authToken)}
                .orElse(false)
    }
}