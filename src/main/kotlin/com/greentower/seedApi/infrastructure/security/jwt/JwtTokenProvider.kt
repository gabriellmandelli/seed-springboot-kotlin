package com.greentower.seedApi.infrastructure.security.jwt

import com.greentower.seedApi.infrastructure.config.JwtConfig
import com.greentower.seedApi.infrastructure.security.CustomAuthUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtTokenProvider(private val jwtConfig: JwtConfig) {

    val TOKEN_PREFIX = "Bearer "

    fun generateTokenByUser(userDetails: UserDetails) : String {
        val expirationDate : Date = Date.from(LocalDateTime.now().plusMinutes(jwtConfig.expirationTimeMinutes.toLong()).atZone(ZoneId.systemDefault()).toInstant())
        return Jwts.builder()
                .setSubject(userDetails.username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.signingKey)
                .compact()
    }

    fun generateTokenByUserWithPrefix(userDetails: UserDetails) : String{
        return TOKEN_PREFIX.plus(this.generateTokenByUser(userDetails))
    }

    fun getAllClaimsFromToken(token: String): Claims {
        return Jwts
                .parser()
                .setSigningKey(jwtConfig.signingKey)
                .parseClaimsJws(token)
                .body
    }

    fun isTokenExpired(authToken : String) : Boolean {
        return try {
            LocalDateTime.now().isAfter(getAllClaimsFromToken(authToken).expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        }catch (exception : Exception){
            true
        }
    }

    fun validateToken(authToken: String, customAuthUser : CustomAuthUser) : Boolean{
        return this.getUsernameFromToken(authToken)
                .map { username -> username.equals(customAuthUser.username) &&  !isTokenExpired(authToken)}
                .orElse(false)
    }

    fun getUsernameFromToken(token: String) : Optional<String> {
        return Optional.ofNullable(getAllClaimsFromToken(token).subject)
    }

    fun getAuthentication(authToken : String, customAuthUser : CustomAuthUser) : UsernamePasswordAuthenticationToken{
        return UsernamePasswordAuthenticationToken(customAuthUser, "", customAuthUser.authorities)
    }
}