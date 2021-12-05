package com.cedricakrou.artisanat.domain.account.entity.client

import com.cedricakrou.artisanat.domain.account.entity.Role
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@DiscriminatorValue( value = UserType.CLIENT)
@JsonIgnoreProperties( ignoreUnknown = true )
class Client() : User() {

    var accountConfirmed : Boolean = false

    @Column( columnDefinition = "boolean default false" )
    var delete : Boolean = false

    @Column( columnDefinition = "boolean default false" )
    var block : Boolean = false

    constructor( username: String, password: String, roles : Set<Role> = setOf() ) : this() {

        this.username = username
        this.password = password
        this.roles = roles
    }

    @OneToMany( mappedBy = "client" )
    @JsonIgnore
    var announcements : MutableList<Announcement> = mutableListOf()

}