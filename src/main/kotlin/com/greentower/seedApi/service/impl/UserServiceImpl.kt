package com.greentower.seedApi.service.impl

import com.greentower.seedApi.domain.entity.User
import com.greentower.seedApi.domain.repository.UserRepository
import com.greentower.seedApi.service.UserService
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserServiceImpl(private var repository: UserRepository) : UserService {

    @Transactional
    override fun save(user: User): User {
        return repository.save(user)
    }

    @Transactional
    override fun update(user: User, id : UUID): User {

        val userDb : Optional<User> = repository.findById(id)

        if (userDb.isPresent){
            user.id = userDb.get().id
            user.password = userDb.get().password
            user.userName = userDb.get().userName
            user.email = userDb.get().email
        }

        return repository.save(user)
    }

    override fun findAll(): List<User> {
        return repository.findAll()
    }

    override fun findById(id: UUID): Optional<User> {
        return repository.findById(id)
    }

    override fun findByUserName(userName: String): Optional<User> {
        return repository.findByuserName(userName)
    }

    @Transactional
    override fun deleteById(id: UUID) {
        return repository.deleteById(id)
    }

    @Transactional
    override fun deleteAll() {
        return repository.deleteAll()
    }
}