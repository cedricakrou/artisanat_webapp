package com.cedricakrou.artisanat.domain.account.account.worker

import com.cedricakrou.artisanat.data.repositories.AccountRepository
import com.cedricakrou.artisanat.domain.account.account.entity.Account
import com.cedricakrou.artisanat.domain.common.OperationResult
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class AccountWorker( val repositoryAccount: AccountRepository) : AccountDomain {

    override fun save(model: Account): OperationResult<Account> {

        var account : Account = model

        val errors : HashMap<String, String> = HashMap()

        account = repositoryAccount.save( model )

        return OperationResult( errors, account )
    }

    override fun findById(id: Long): Optional<Account> =  repositoryAccount.findById(id)

    override fun findAll(): List<Account> = repositoryAccount.findAll()

    override fun deleteAll() = repositoryAccount.deleteAll()

    override fun deleteById(id: Long) = repositoryAccount.deleteById(id)

    override fun count(): Long  = repositoryAccount.count()

    override fun findByAccountNumber(accountNumber: String): Optional<Account> = repositoryAccount.findByAccountNumber(accountNumber)
}