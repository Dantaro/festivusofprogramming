package com.festivusofprogramming.internal.session

import com.festivusofprogramming.internal.user.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionService(
    private val sessionRepository: SessionRepository
) {

    fun createSession(user: User): String {
        val id = UUID.randomUUID().toString()
        sessionRepository.save(
            UserSessionEntity(
                id = id,
                userId = user.id
            )
        )

        return "${user.id}_$id"
    }

    fun checkSession(sessionValue: String?): Boolean {
        // If we don't have a session cookie value, they aren't logged in
        if (sessionValue == null) {
            return false
        }
        // Break down the session cookie
        val valueArray = sessionValue.split("_")
        // If the session cookie is malformed they aren't logged in
        if (valueArray.size != 2) {
            return false
        }
        // Find the session
        val session = sessionRepository.getUserSessionEntitiesByUserIdAndId(
            userId = valueArray[0],
            id = valueArray[1]
        )
        // If the user has no session, they aren't logged in
        return session != null
    }

    fun redactSession(sessionValue: String?) {
        // If we don't have a session cookie value, they aren't logged in
        if (sessionValue == null) {
            return
        }
        // Break down the session cookie
        val valueArray = sessionValue.split("_")
        // If the session cookie is malformed they aren't logged in
        if (valueArray.size != 2) {
            return
        }
        // Redact the session
        sessionRepository.deleteByUserIdAndId(
            userId = valueArray[0],
            id = valueArray[1]
        )
    }
}