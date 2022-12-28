package com.festivusofprogramming.internal.user

enum class AuthType(val id: String) {
    GITHUB("aa191cd3-50b3-4dbd-b838-7d6e0c40e487");

    companion object {
        private val map = AuthType.values().associateBy { it.id }
        fun getAuthTypeById(id: String): AuthType? = map[id]
    }
}