package com.greentower.seedApi.user.service.impl

import com.greentower.seedApi.infrastructure.generic.GenericRepository
import com.greentower.seedApi.infrastructure.generic.GenericServiceImpl
import com.greentower.seedApi.user.domain.entity.AuthUser
import com.greentower.seedApi.user.domain.enum.UserRole
import com.greentower.seedApi.user.domain.repository.AuthUserRepository
import com.greentower.seedApi.user.rest.exception.AuthUserNotFoundException
import com.greentower.seedApi.user.service.AuthUserService
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
class AuthUserServiceImpl : AuthUserService, GenericServiceImpl<AuthUser>() {

    @Autowired
    lateinit var repositoryAuthUser: AuthUserRepository<AuthUser>

    @Autowired
    lateinit var passwordEncoder : PasswordEncoder

    override fun save(entity: AuthUser): AuthUser {
        entity.password = passwordEncoder.encode(entity.password)
        return repository.save(entity)
    }

    override fun update(entity: AuthUser, id : UUID): AuthUser {

        val authUserFromDb : Optional<AuthUser> = repository.findById(id)

        if (authUserFromDb.isPresent){
            entity.id = authUserFromDb.get().id
            entity.password = authUserFromDb.get().password
        }else{
            throw AuthUserNotFoundException("User not found")
        }

        return repository.save(entity)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails {

        val authUser = findByUserName(userName)

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

    override fun findByUserName(userName: String): AuthUser {
        return repositoryAuthUser.findByusername(userName)
                .orElseThrow{AuthUserNotFoundException("User not found")}
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
