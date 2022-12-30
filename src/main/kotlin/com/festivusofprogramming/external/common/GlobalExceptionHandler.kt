package com.festivusofprogramming.external.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [UnauthorizedUserException::class])
    fun handleUnauthorizedUserException(e: Exception): ResponseEntity<Any?> {
        return ResponseEntity(
            Response<Unit>(
                Error(listOf("error" to "Unauthorized User")),
                null
            ),
            HttpStatus.UNAUTHORIZED
        )
    }

}