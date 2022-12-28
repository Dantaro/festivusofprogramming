package com.festivusofprogramming.internal.auth

import com.festivusofprogramming.internal.user.User

interface AuthService {
    fun handleAuthentication(authenticationResult: AuthenticationResult): User
}

data class AuthenticationResult(
    val scope: String? = null,
    val accessToken: String? = null,
    val tokenType: String? = null,
    val error: Boolean
)
