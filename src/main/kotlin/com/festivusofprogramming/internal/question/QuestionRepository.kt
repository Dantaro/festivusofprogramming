package com.festivusofprogramming.internal.question

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "question")
class QuestionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val questionYear: Int?,
    val questionNumber: Int?,
    val answer: String?,
    @Column(insertable = false, updatable = false)
    val createdOn: LocalDateTime? = null,
    @Column(insertable = false, updatable = false)
    val updatedOn: LocalDateTime? = null
)

interface QuestionRepository: JpaRepository<QuestionEntity, Long> {
    fun findByQuestionYearAndQuestionNumber(questionYear: Int?, questionNumber: Int?): QuestionEntity?
}