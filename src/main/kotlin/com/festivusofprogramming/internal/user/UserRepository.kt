package com.festivusofprogramming.internal.user

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "user")
class UserEntity(
    @Id
    val id: String,
    val identifier: String,
    val authTypeId: String,
    @Column(insertable = false, updatable = false)
    val createdOn: LocalDateTime? = null,
    @Column(insertable = false, updatable = false)
    val updatedOn: LocalDateTime? = null
)
interface UserRepository: JpaRepository<UserEntity, String> {
    fun getFirstByIdentifierAndAuthTypeId(identifier: String, authTypeId: String): UserEntity?
}