package com.festivusofprogramming.internal.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUser(identifier: String, authType: AuthType): User? {
        // Check DB
        return userRepository
            .getFirstByIdentifierAndAuthTypeId(identifier, authType.id)
            ?.let { User(
                id = it.id,
                identifier = it.identifier,
                authType = AuthType.getAuthTypeById(it.authTypeId)!!
            ) }
    }

    fun getUserById(id: String): User? {
        return userRepository
            .findById(id)
            .map { User(
                id = it.id,
                identifier = it.identifier,
                authType = AuthType.getAuthTypeById(it.authTypeId)!!
            ) }
            .orElse(null)
    }

    @Transactional(propagation = Propagation.MANDATORY)
    fun createUser(identifier: String, authType: AuthType): User {
        val user = UserEntity(
            id = UUID.randomUUID().toString(),
            identifier = identifier,
            authTypeId = authType.id
        )
        userRepository.save(user)
        // Save to DB
        return User(
            id = user.id,
            identifier = identifier,
            authType = authType
        )
    }
}