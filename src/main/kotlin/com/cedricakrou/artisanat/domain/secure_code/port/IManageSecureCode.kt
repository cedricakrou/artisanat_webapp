package com.cedricakrou.artisanat.domain.secure_code.port

import com.cedricakrou.artisanat.domain.secure_code.entity.SecureCode
import com.cedricakrou.artisanat.domain.common.port.BasePort
import java.util.*

interface IManageSecureCode : BasePort<SecureCode> {
    fun findByMemberNoAndCode( memberNo : String, code : String ) : Optional<SecureCode>
}