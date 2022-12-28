package com.festivusofprogramming.external.auth

import com.festivusofprogramming.internal.session.SessionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SessionApiService(
    private val sessionService: SessionService
) {

    @Transactional
    fun redactCookie(cookieValue: String?) {
        sessionService.redactSession(cookieValue)
    }
}