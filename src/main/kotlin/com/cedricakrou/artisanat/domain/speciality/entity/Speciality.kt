package com.cedricakrou.artisanat.domain.speciality.entity

import com.cedricakrou.artisanat.domain.account.entity.Artisan
import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Speciality( var name : String = "" ) : BaseTableEntity() {

    @OneToMany( mappedBy = "speciality" )
    @JsonIgnore
    var artisans : MutableList<Artisan> = mutableListOf()

    @OneToMany( mappedBy = "speciality" )
    var announcements : MutableList<Announcement> = mutableListOf()

}