package com.greentower.seedApi.config

import com.greentower.seedApi.domain.enum.UserRole
import com.greentower.seedApi.security.jwt.JwtAuthFilter
import com.greentower.seedApi.security.jwt.JwtService
import com.greentower.seedApi.service.AuthUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var authUserService : AuthUserService

    @Autowired
    lateinit var jwtService: JwtService

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtFilter() : OncePerRequestFilter {
        return JwtAuthFilter(authUserService, jwtService)
    }

    override fun configure( authenticationManagerBuilder : AuthenticationManagerBuilder){
        authenticationManagerBuilder
                .userDetailsService(authUserService)
                .passwordEncoder(passwordEncoder())
    }

    override  fun configure( httpSecurity: HttpSecurity ){
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/user/**")
                        .hasRole(UserRole.ADMIN.toString())
                    .antMatchers(HttpMethod.POST, "/auth/**")
                        .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(webSecurity: WebSecurity){
        webSecurity.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**")
    }
}