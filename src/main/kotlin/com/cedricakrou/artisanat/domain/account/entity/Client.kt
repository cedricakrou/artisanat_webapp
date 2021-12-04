package com.cedricakrou.artisanat.domain.account.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@DiscriminatorValue( value = UserType.CLIENT)
@JsonIgnoreProperties( ignoreUnknown = true )
class Client() : User() {


    constructor( username: String, password: String, roles : Set<Role> = setOf() ) : this() {

        this.username = username
        this.password = password
        this.roles = roles
    }

}