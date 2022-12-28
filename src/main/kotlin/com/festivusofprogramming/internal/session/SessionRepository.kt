package com.festivusofprogramming.internal.session

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "user_session")
class UserSessionEntity(
    @Id
    val id: String,
    val userId: String,
    @Column(insertable = false, updatable = false)
    val createdOn: LocalDateTime? = null,
    @Column(insertable = false, updatable = false)
    val updatedOn: LocalDateTime? = null
)

interface SessionRepository: JpaRepository<UserSessionEntity, String> {
    fun getUserSessionEntitiesByUserIdAndId(userId: String, id: String): UserSessionEntity?
    fun deleteByUserIdAndId(userId: String, id: String)
}