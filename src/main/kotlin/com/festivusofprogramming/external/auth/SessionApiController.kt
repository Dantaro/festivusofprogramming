package com.festivusofprogramming.external.auth

import com.festivusofprogramming.external.common.AuthUserService
import com.festivusofprogramming.external.common.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth/session")
class SessionApiController(
    private val sessionApiService: SessionApiService,
    private val authUserService: AuthUserService,
    @Value("\${festivus.cookie.session.name}") private val sessionCookieName: String
) {

    @GetMapping("/check")
    fun checkForSession(request: HttpServletRequest): Response<Boolean> {
        // This throws an error that leads to a 401 if the user isn't logged in
        authUserService.provideAuthUser(request)
        return Response(null, true)
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse) {
        val sessionCookie = request.cookies.firstOrNull { it.name == sessionCookieName}
        sessionApiService.redactCookie(sessionCookie?.value)
        response.addCookie(authUserService.buildCookie(null, 0))
    }
}