package com.cedricakrou.artisanat.domain.account.entity.artisan

import com.cedricakrou.artisanat.domain.account.entity.Role
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@DiscriminatorValue( value = UserType.ARTISAN)
@JsonIgnoreProperties( ignoreUnknown = true )
class Artisan() : User() {

    var visible : Boolean = false

    var phoneNumber1: String = ""
    var phoneNumber2: String = ""
    var biography: String = ""

    var cniRecto : String = ""
    var cniVerso : String = ""


    @ManyToOne
    var speciality : Speciality? = null

    constructor( username: String, password: String, roles : Set<Role> = setOf() ) : this() {

        this.username = username
        this.password = password
        this.roles = roles
    }

}