package com.greentower.seedApi.user.service.impl

import com.greentower.seedApi.infrastructure.generic.GenericServiceImpl
import com.greentower.seedApi.infrastructure.security.CustomAuthUser
import com.greentower.seedApi.user.domain.entity.AuthUser
import com.greentower.seedApi.user.domain.exception.AuthUserResponseStatusMessage
import com.greentower.seedApi.user.domain.repository.AuthUserRepository
import com.greentower.seedApi.user.service.AuthUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AuthUserServiceImpl : AuthUserService, GenericServiceImpl<AuthUser>() {

    @Autowired
    private lateinit var repositoryAuthUser: AuthUserRepository<AuthUser>

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    fun setService(){
        repository = repositoryAuthUser
    }

    override fun save(entity: AuthUser): AuthUser {
        entity.password = passwordEncoder.encode(entity.password)
        return repository.save(entity)
    }

    override fun update(entity: AuthUser, id : UUID): AuthUser {

        val authUserFromDb : Optional<AuthUser> = repository.findById(id)

        if (authUserFromDb.isPresent){
            entity.id = authUserFromDb.get().id
            entity.username = authUserFromDb.get().username
            entity.password = authUserFromDb.get().password
        }else{
            throw AuthUserResponseStatusMessage.getResponseNotFound()
        }

        return repository.save(entity)
    }

    override fun updatePassword(username: String, oldPassword : String, newPassword: String): AuthUser{

        val authUserFromDB = findByUserName(username)

        if (authUserFromDB.isPresent){
            if (passwordEncoder.matches(oldPassword, authUserFromDB.get().password)){
                authUserFromDB.get().password = passwordEncoder.encode(newPassword)
            }else{
                throw AuthUserResponseStatusMessage.getResponsePasswordNotMatches()
            }
        } else {
            throw AuthUserResponseStatusMessage.getResponseNotFound()
        }

        return repository.save(authUserFromDB.get())
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): CustomAuthUser {
        val authUser = findByUserName(userName).orElseThrow{ AuthUserResponseStatusMessage.getResponsePasswordNotMatches() }
        return CustomAuthUser(authUser.id, authUser.username, authUser.password, AuthorityUtils.createAuthorityList("ROLE_"+authUser.role.toString()))
    }

    override fun findByUserName(userName: String): Optional<AuthUser> {
        return repositoryAuthUser.findByusername(userName)
    }

    override fun authenticate(authUser: AuthUser): CustomAuthUser {
        val customAuthUser = loadUserByUsername(authUser.username)

        if (passwordEncoder.matches(authUser.password, customAuthUser.password)){
            return customAuthUser
        }else{
            throw  AuthUserResponseStatusMessage.getResponsePasswordNotMatches()
        }
    }
}
