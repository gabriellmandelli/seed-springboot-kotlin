package com.greentower.seedApi.infrastructure.config

import com.greentower.seedApi.infrastructure.security.jwt.JwtAuthenticationFilter
import com.greentower.seedApi.infrastructure.security.jwt.JwtTokenProvider
import com.greentower.seedApi.user.domain.enum.UserRole
import com.greentower.seedApi.user.service.AuthUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
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
    lateinit var jwtTokenProvider: JwtTokenProvider

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtFilter() : OncePerRequestFilter {
        return JwtAuthenticationFilter(authUserService, jwtTokenProvider)
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
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/user/**").hasRole(UserRole.ADMIN.toString())
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }
}