package com.festivusofprogramming.external.question

import com.festivusofprogramming.internal.question.QuestionService
import com.festivusofprogramming.internal.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionApiService(
    private val questionService: QuestionService
) {
    fun retrieveUserSuccesses(user: User): List<UserQuestionSuccessApiResponse> {
        return questionService
            .getUserSuccesses(user.id)
            .map { UserQuestionSuccessApiResponse(it.questionId) }
    }

    /**
     * Checks if a user has a correct answer for a given problem, and stores the success if it is
     */
    @Transactional
    fun checkAnswer(user: User, form: CheckUserAnswerRequest): Boolean {
        val question = questionService.getQuestion(form.questionYear, form.questionNumber)
        val success = question
            ?.let { it.answer == form.answer }
            ?: false

        //If they answered correctly, and they don't have an existing success record
        if (success && !questionService.checkForExistingUserSuccess(user.id, question!!.id)) {
            questionService.saveSuccess(user, question)
        }

        return success
    }
}

data class UserQuestionSuccessApiResponse(
    val questionId: Long
)