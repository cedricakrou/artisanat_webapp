package com.cedricakrou.artisanat.domain.account.port

import com.cedricakrou.artisanat.domain.account.entity.Role
import java.util.*

interface IManageRole {

    fun save(role : Role) : Role
    fun count() : Long
    fun findByName(name : String) : Optional<Role>
    fun findById(  id : Long ) : Optional<Role>
    fun findAll() : List<Role>

}