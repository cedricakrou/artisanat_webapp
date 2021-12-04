package com.cedricakrou.artisanat.domain.account.worker

import com.cedricakrou.artisanat.domain.account.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String) : Optional<Role>

}