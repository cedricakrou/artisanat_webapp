package com.cedricakrou.artisanat.domain.account.port

import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.vm.UserVm
import com.cedricakrou.artisanat.domain.common.OperationResult
import java.util.*

interface IManageUser {

    fun save( user: User) : OperationResult<User>

    fun findById( id : Long ) : Optional<User>

    fun findByUsername( username : String) : Optional<User>

    fun findByEmail( email : String ) : Optional<User>

    fun findTypeBy(id: Long): String

    fun count() : Long

    fun changeStatut( user : User) : OperationResult<User>

    fun savePin( user: User) : OperationResult<User>

    fun changePassword( userVm: UserVm) : OperationResult<UserVm>

    fun saveData(user : User )
}