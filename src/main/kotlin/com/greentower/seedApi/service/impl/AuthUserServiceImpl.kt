package com.greentower.seedApi.service.impl

import com.greentower.seedApi.domain.entity.AuthUser
import com.greentower.seedApi.domain.enum.UserRole
import com.greentower.seedApi.domain.repository.AuthUserRepository
import com.greentower.seedApi.rest.exception.AuthUserNotFoundException
import com.greentower.seedApi.service.AuthUserService
import com.greentower.seedApi.util.generic.GenericServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AuthUserServiceImpl : AuthUserService, GenericServiceImpl<AuthUser, UUID>() {

    @Autowired
    lateinit var repositoryAuthUser: AuthUserRepository

    @Autowired
    lateinit var passwordEncoder : PasswordEncoder

    @Transactional
    override fun save(entity: AuthUser): AuthUser {
        entity.password = passwordEncoder.encode(entity.password)
        return repository.save(entity)
    }

    @Transactional
    override fun update(entity: AuthUser, id : UUID): AuthUser {

        val authUserFromDb : Optional<AuthUser> = repository.findById(id)

        if (authUserFromDb.isPresent){
            entity.id = authUserFromDb.get().id
            entity.password = authUserFromDb.get().password
            entity.username = authUserFromDb.get().username
            entity.email = authUserFromDb.get().email
        }

        return repository.save(entity)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails {

        val authUser : AuthUser = repositoryAuthUser.findByusername(userName)
                .orElseThrow{AuthUserNotFoundException("User not found")}

        val roles : Array<String>

        roles = if( authUser.role == UserRole.ADMIN){
            arrayOf(UserRole.ADMIN.toString(), UserRole.USER.toString())
        }else{
            arrayOf(UserRole.USER.toString())
        }

        return User
                .builder()
                .username(authUser.username)
                .password(authUser.password)
                .roles(*roles)
                .build()
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
}
