package com.festivusofprogramming.internal.question

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "user_question_success")
class UserQuestionSuccessEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val userId: String?,
    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    val question: QuestionEntity?,
    @Column(insertable = false, updatable = false)
    val createdOn: LocalDateTime? = null,
    @Column(insertable = false, updatable = false)
    val updatedOn: LocalDateTime? = null
)

interface UserQuestionSuccessRepo: JpaRepository<UserQuestionSuccessEntity, Long> {
    fun findAllByUserId(userId: String): List<UserQuestionSuccessEntity>

    fun findByUserIdAndQuestionId(userId: String, questionId: Long): UserQuestionSuccessEntity?
}