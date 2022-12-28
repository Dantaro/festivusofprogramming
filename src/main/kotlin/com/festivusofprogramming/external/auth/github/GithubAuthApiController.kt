package com.festivusofprogramming.external.auth.github

import com.festivusofprogramming.external.common.AuthUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth/github")
class GithubAuthApiController(
    private val githubAuthApiService: GithubAuthApiService,
    private val authUserService: AuthUserService,
) {

    @GetMapping("/")
    fun authenticate(
        @RequestParam("code") code: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        try {
            response.addCookie(
                authUserService.buildCookie(githubAuthApiService.authenticate(code))
            )
        } catch (e: Exception) {
            // Auth failed for whatever reason
        }
    }
}
