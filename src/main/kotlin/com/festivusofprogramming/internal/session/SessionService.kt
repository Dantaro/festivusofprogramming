package com.festivusofprogramming.internal.session

import com.festivusofprogramming.internal.user.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime
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
        // If the user has no session, they aren't logged in
        val session = sessionRepository.getUserSessionEntitiesByUserIdAndId(
            userId = valueArray[0],
            id = valueArray[1]
        ) ?: return false

        // If the user has a session but the record is too old, they aren't logged in
        // (and also remove the record)
        // If the current time 14 days ago is still before the session's creation time (meaning
        // the session was created AFTER that time) then the session is still valid
        return if (LocalDateTime.now().minusDays(14).isBefore(session.createdOn)) {
            true
        } else {
            sessionRepository.deleteByUserIdAndId(
                userId = valueArray[0],
                id = valueArray[1]
            )
            false
        }
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