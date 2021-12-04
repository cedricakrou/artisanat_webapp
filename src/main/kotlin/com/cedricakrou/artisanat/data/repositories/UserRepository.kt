package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.account.entity.User
import com.b2i.neo.domain.common.port.ReferencePort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>, ReferencePort<User> {

    fun findByUsername(username: String) : Optional<User>

    fun findByEmail(username: String) : Optional<User>

    @Query(value = "SELECT U.user_type FROM user_account U WHERE U.id = ?1", nativeQuery = true)
    fun findTypeBy(id: Long): String

    fun countByUsername( username: String ) : Long

    fun countByEmail( email : String ) : Long


}