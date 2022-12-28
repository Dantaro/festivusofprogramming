package com.festivusofprogramming.internal.user

data class User(
    val id: String,
    val identifier: String,
    val authType: AuthType
)
