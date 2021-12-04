package com.cedricakrou.artisanat.domain.account.entity

import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class Person : BaseTableEntity() {

    open var firstname: String = ""
    open var lastname: String = ""
    open var email: String = ""
    open var phoneNumber: String = ""
    open var birthDate : String = ""
    open var marriedStatus : String = ""
    open var sex : String = ""
    open var address : String = ""

/**
    @ManyToOne
    open var country : Country? = null

    @ManyToOne
    open var city : City? = null
**/
}