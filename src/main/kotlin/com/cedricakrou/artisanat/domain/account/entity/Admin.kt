package com.cedricakrou.artisanat.domain.account.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(UserType.ADMIN)
class Admin() : User() {

    constructor( username: String, password: String, roles : Set<Role> = setOf() ) : this() {

        this.username = username
        this.password = password
        this.roles = roles

    }

}