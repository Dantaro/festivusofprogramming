package com.festivusofprogramming.external.question

import com.festivusofprogramming.external.common.AuthUserService
import com.festivusofprogramming.external.common.Response
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/question")
class QuestionApiController(
    private val questionApiService: QuestionApiService,
    private val authUserService: AuthUserService
) {

    @GetMapping("/users-successes")
    fun getUserQuestionSuccesses(
        request: HttpServletRequest
    ): Response<List<UserQuestionSuccessApiResponse>> {
        val user = authUserService.provideAuthUser(request)
        return Response(null, questionApiService.retrieveUserSuccesses(user))
    }

    @PostMapping("/check")
    fun checkUserAnswer(
        @RequestBody checkUserAnswerRequest: CheckUserAnswerRequest,
        request: HttpServletRequest,
    ): Response<Boolean> {
        val user = authUserService.provideAuthUser(request)
        return Response(null, questionApiService.checkAnswer(user, checkUserAnswerRequest))
    }
}

data class CheckUserAnswerRequest(
    val answer: String,
    val questionYear: Int,
    val questionNumber: Int
)
