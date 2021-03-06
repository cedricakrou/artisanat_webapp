package com.cedricakrou.artisanat.domain.speciality.entity

import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Speciality( var name : String = "", var description : String = "" ) : BaseTableEntity() {

    @OneToMany( mappedBy = "speciality" )
    @JsonIgnore
    var artisans : MutableList<Artisan> = mutableListOf()

    @OneToMany( mappedBy = "speciality" )
    @JsonIgnore
    var announcements : MutableList<Announcement> = mutableListOf()

}