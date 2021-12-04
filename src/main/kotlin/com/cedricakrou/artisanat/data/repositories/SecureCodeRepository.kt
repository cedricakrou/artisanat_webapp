package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.secure_code.entity.SecureCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SecureCodeRepository : JpaRepository<SecureCode, Long> {

    fun findByMemberNoAndCode( memberNo : String, code : String ) : Optional<SecureCode>

}