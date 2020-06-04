package com.greentower.seedApi.user.service.impl

import com.greentower.seedApi.infrastructure.generic.GenericServiceImpl
import com.greentower.seedApi.infrastructure.security.CustomAuthUser
import com.greentower.seedApi.infrastructure.util.exception.ResponseStatusExceptionToLocate
import com.greentower.seedApi.user.domain.entity.AuthUser
import com.greentower.seedApi.user.domain.repository.AuthUserRepository
import com.greentower.seedApi.user.service.AuthUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.authority.AuthorityUtils
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

    @Autowired
    fun setService(){
        repository = repositoryAuthUser
    }

    private fun getResponseNotFound() : ResponseStatusExceptionToLocate{
        return ResponseStatusExceptionToLocate(HttpStatus.NOT_FOUND, "entity.auth_user.not_found")
    }

    private fun getResponsePasswordNotMatches() : ResponseStatusExceptionToLocate{
        return ResponseStatusExceptionToLocate(HttpStatus.UNAUTHORIZED, "entity.auth_user.password_not_matches")
    }

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
            throw getResponseNotFound()
        }

        return repository.save(entity)
    }

    override fun updatePassword(username: String, oldPassword : String, newPassword: String): AuthUser{

        val authUserFromDB = findByUserName(username)

        if (passwordEncoder.matches(authUserFromDB.password, passwordEncoder.encode(oldPassword))){
            authUserFromDB.password = passwordEncoder.encode(newPassword)
            return repository.save(authUserFromDB)
        }else{
            throw  getResponsePasswordNotMatches()
        }
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails {
        val authUser = findByUserName(userName)
        return CustomAuthUser(authUser.id, authUser.username, authUser.password, AuthorityUtils.createAuthorityList("ROLE_"+authUser.role.toString()))
    }

    override fun findByUserName(userName: String): AuthUser {
        return repositoryAuthUser.findByusername(userName)
                .orElseThrow{ getResponseNotFound() }
    }

    override fun authenticate(authUser: AuthUser): UserDetails {
        val userDetails = loadUserByUsername(authUser.username)

        if (passwordEncoder.matches(authUser.password, userDetails.password)){
            return userDetails
        }else{
            throw  getResponsePasswordNotMatches()
        }
    }
}
