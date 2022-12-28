package com.festivusofprogramming.internal.auth

import com.festivusofprogramming.internal.user.AuthType
import com.festivusofprogramming.internal.user.User
import com.festivusofprogramming.internal.user.UserService
import com.festivusofprogramming.util.JacksonProvider
import com.festivusofprogramming.util.OkHttpProvider
import okhttp3.Request
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("githubAuthService")
class GithubAuthService(
    private val okHttpProvider: OkHttpProvider,
    private val jacksonProvider: JacksonProvider,
    private val userService: UserService
): AuthService {

    @Transactional
    override fun handleAuthentication(authenticationResult: AuthenticationResult): User {
        val client = okHttpProvider.getClient()
        val request = Request
            .Builder()
            .addHeader(
                "Authorization",
                "${authenticationResult.tokenType} ${authenticationResult.accessToken}"
            )
            .url("https://api.github.com/user")
            .get()
            .build()
        val response = client.newCall(request).execute()
        val githubUser = jacksonProvider.getMapper().readValue(
            response.body?.string(),
            GithubUserResponse::class.java
        )

        val user = userService.getUser(githubUser.id, AuthType.GITHUB)

        return user ?: userService.createUser(githubUser.id, AuthType.GITHUB)
    }
}


data class GithubUserResponse(val id: String)
