package com.cedricakrou.artisanat.domain.account.account.entity

import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.persistence.*

@Entity
@DiscriminatorColumn(name ="account_type", length = 20)
@Inheritance( strategy =  InheritanceType.SINGLE_TABLE)
open class Account : BaseTableEntity() {

    @JsonIgnoreProperties( value = [ "password", "roles", "accounts"] )
    @ManyToOne
    var user : User? = null

    var accountNumber : String = ""
    var balance : Double = 0.0
    var dateCreation : Date = Date()

}