package com.festivusofprogramming.external.common

data class Response<R>(val error: Error?, val result: R?)

data class Error(val errors: List<Pair<String, String>>)
