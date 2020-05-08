package com.greentower.seedApi.service.impl

import com.greentower.seedApi.domain.entity.AuthUser
import com.greentower.seedApi.domain.enum.UserRole
import com.greentower.seedApi.domain.repository.AuthUserRepository
import com.greentower.seedApi.rest.exception.AuthUserNotFoundException
import com.greentower.seedApi.service.AuthUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class AuthUserServiceImpl() : AuthUserService {

    @Autowired
    lateinit var repositoryAuth: AuthUserRepository

    @Autowired
    lateinit var passwordEncoder : PasswordEncoder

    @Transactional
    override fun save(authUser: AuthUser): AuthUser {
        authUser.password = passwordEncoder.encode(authUser.password)
        return repositoryAuth.save(authUser)
    }

    @Transactional
    override fun update(authUser: AuthUser, id : UUID): AuthUser {

        val authUserFromDb : Optional<AuthUser> = repositoryAuth.findById(id)

        if (authUserFromDb.isPresent){
            authUser.id = authUserFromDb.get().id
            authUser.password = authUserFromDb.get().password
            authUser.username = authUserFromDb.get().username
            authUser.email = authUserFromDb.get().email
        }

        return repositoryAuth.save(authUser)
    }

    override fun findAll(): List<AuthUser> {
        return repositoryAuth.findAll()
    }

    override fun findById(id: UUID): Optional<AuthUser> {
        return repositoryAuth.findById(id)
    }

    @Transactional
    override fun deleteById(id: UUID) {
        return repositoryAuth.deleteById(id)
    }

    @Transactional
    override fun deleteAll() {
        return repositoryAuth.deleteAll()
    }

    override fun authenticate(authUser: AuthUser): UserDetails {
        val userDetails = loadUserByUsername(authUser.username)
        val isValidPassword = passwordEncoder.matches(authUser.password, userDetails.password)

        if (isValidPassword){
            return userDetails
        }else{
            throw  RuntimeException("Password not matches")
        }
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails {
        val authUser : AuthUser = repositoryAuth.findByusername(userName)
                .orElseThrow{AuthUserNotFoundException("User not found")}

        val roles : Array<String>

        roles = if( authUser.role == UserRole.ADMIN){
            arrayOf(UserRole.ADMIN.toString(), UserRole.USER.toString())
        }else{
            arrayOf(UserRole.USER.toString())
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(authUser.username)
                .password(authUser.password)
                .roles(*roles)
                .build()
    }
}
