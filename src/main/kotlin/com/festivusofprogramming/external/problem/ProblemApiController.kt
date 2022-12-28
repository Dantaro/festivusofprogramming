package com.festivusofprogramming.external.problem

import com.festivusofprogramming.external.common.Response
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/problems")
class ProblemApiController {

    fun getProblemList(): Response<List<Any>> {
        TODO()
    }

    fun getUserProblemList(): Response<List<Any>> {
        TODO()
    }

}