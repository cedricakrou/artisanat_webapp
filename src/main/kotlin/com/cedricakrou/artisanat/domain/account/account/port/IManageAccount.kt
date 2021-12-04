package com.cedricakrou.artisanat.domain.account.account.port

import com.cedricakrou.artisanat.domain.account.account.entity.Account
import com.cedricakrou.artisanat.domain.common.port.BasePort
import java.util.*

interface IManageAccount : BasePort<Account> {

    fun findByAccountNumber( accountNumber : String ) : Optional<Account>

}