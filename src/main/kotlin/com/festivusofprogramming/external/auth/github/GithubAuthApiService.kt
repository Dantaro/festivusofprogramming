package com.festivusofprogramming.external.auth.github

import com.fasterxml.jackson.annotation.JsonAlias
import com.festivusofprogramming.internal.auth.AuthService
import com.festivusofprogramming.internal.auth.AuthenticationResult
import com.festivusofprogramming.internal.session.SessionService
import com.festivusofprogramming.util.JacksonProvider
import com.festivusofprogramming.util.OkHttpProvider
import okhttp3.FormBody
import okhttp3.Request
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GithubAuthApiService(
    @Qualifier("githubAuthService") private val authService: AuthService,
    private val sessionService: SessionService,
    private val okHttpProvider: OkHttpProvider,
    private val jacksonProvider: JacksonProvider,
    @Value("\${github.clientId}") private val clientId: String,
    @Value("\${github.clientSecret}") private val clientSecret: String,
) {

    fun authenticate(code: String): String {
        val client = okHttpProvider.getClient()
        val request = Request
            .Builder()
            .addHeader("Accept", "application/json")
            .url("https://github.com/login/oauth/access_token")
            .post(
                FormBody
                    .Builder()
                    .add("client_id", clientId)
                    .add("client_secret", clientSecret)
                    .add("code", code)
                    .build()
            )
            .build()
        val response = client.newCall(request).execute()
        val authResponse = jacksonProvider
            .getMapper()
            .readValue(response.body?.string(), GithubAuthResponse::class.java)
        val user = authService.handleAuthentication(
            AuthenticationResult(
                error = false,
                tokenType = authResponse.tokenType,
                scope = authResponse.scope,
                accessToken = authResponse.accessToken
            )
        )
        return sessionService.createSession(user)
    }
}

data class GithubAuthResponse(
    @JsonAlias("access_token") val accessToken: String? = null,
    val scope: String? = null,
    @JsonAlias("token_type") val tokenType: String? = null
)
