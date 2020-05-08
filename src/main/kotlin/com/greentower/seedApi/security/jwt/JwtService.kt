package com.greentower.seedApi.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtService {

    @Value("\${security.jwt.expiration-time}")
    var expirationTime = ""

    @Value("\${security.jwt.signing-key}")
    var signingKey = ""

    fun generateTokenByUser( userDetails: UserDetails) : String {

        val expirationDate : Date = Date.from(LocalDateTime.now().plusMinutes(expirationTime.toLong()).atZone(ZoneId.systemDefault()).toInstant())

        return Jwts
                .builder()
                .setSubject(userDetails.username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact()
    }

    fun getClaims(token: String): Claims {
        return Jwts
                .parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .body
    }

    fun isValidToken(token : String) : Boolean {
        return try {
            !LocalDateTime.now().isAfter(getClaims(token).expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        }catch (exception : Exception){
            false
        }
    }

    fun getUseLoginFromToken(token: String) : String {
        return getClaims(token).subject
    }
}