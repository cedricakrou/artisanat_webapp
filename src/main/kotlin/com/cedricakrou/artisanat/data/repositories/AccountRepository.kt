package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.account.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByAccountNumber( accountNumber : String ) : Optional<Account>
}
