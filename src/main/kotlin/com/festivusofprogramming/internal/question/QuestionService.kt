package com.festivusofprogramming.internal.question

import com.festivusofprogramming.internal.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
    private val questionRepository: QuestionRepository,
    private val userQuestionSuccessRepo: UserQuestionSuccessRepo
) {

    fun getUserSuccesses(userId: String): List<UserQuestionSuccess> {
        return userQuestionSuccessRepo
            .findAllByUserId(userId)
            .map { UserQuestionSuccess(
                id = it.id,
                userId = it.userId!!,
                questionId = it.question!!.id!!
            ) }
    }

    fun checkForExistingUserSuccess(userId: String, questionId: Long): Boolean {
        return userQuestionSuccessRepo.findByUserIdAndQuestionId(userId, questionId) != null
    }

    fun getQuestion(questionYear: Int, questionNumber: Int): Question? {
        return questionRepository.findByQuestionYearAndQuestionNumber(questionYear, questionNumber)
            ?.let { Question(
                id = it.id!!,
                questionYear = it.questionYear!!,
                questionNumber = it.questionNumber!!,
                answer = it.answer!!
            ) }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    fun saveSuccess(user: User, question: Question) {
        userQuestionSuccessRepo.save(UserQuestionSuccessEntity(
            id = null,
            userId = user.id,
            question = questionRepository.findById(question.id).get()
        ))
    }
}