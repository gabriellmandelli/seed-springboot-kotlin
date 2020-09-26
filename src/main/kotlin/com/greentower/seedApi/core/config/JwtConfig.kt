package com.greentower.seedApi.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
class JwtConfig {
    var signingKey : String = ""
    var expirationTimeMinutes : Int = 0
}