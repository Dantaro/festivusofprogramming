package com.festivusofprogramming.external.common

import com.festivusofprogramming.internal.session.SessionService
import com.festivusofprogramming.internal.user.User
import com.festivusofprogramming.internal.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

class UnauthorizedUserException : Throwable()

@Service
class AuthUserService(
    private val sessionService: SessionService,
    private val userService: UserService,
    @Value("\${festivus.cookie.session.name}") private val sessionCookieName: String
) {

    fun provideAuthUser(request: HttpServletRequest): User {
        val sessionCookie = request
            .cookies
            ?.firstOrNull { it.name == sessionCookieName }
            ?: throw UnauthorizedUserException()

        if (sessionService.checkSession(sessionCookie.value)) {
            return userService
                .getUserById(sessionCookie.value.split("_")[0])
                ?: throw UnauthorizedUserException()
        } else {
            throw UnauthorizedUserException()
        }
    }

    fun buildCookie(cookieValue: String?, maxAge: Int? = null): Cookie {
        val cookie = Cookie(sessionCookieName, cookieValue)
        cookie.isHttpOnly = true
        cookie.secure = true
        cookie.path = "/"
        maxAge?.also { cookie.maxAge = it }
        return cookie
    }
}