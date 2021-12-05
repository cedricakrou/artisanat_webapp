package com.cedricakrou.artisanat.domain.announcement.entity

import com.cedricakrou.artisanat.domain.account.entity.client.Client
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Announcement : BaseTableEntity() {

    var title : String = ""

    var description : String = ""

    var date : Date = Date()

    var price : Double = 0.0

    @ManyToOne
    lateinit var client : Client

    @ManyToOne
    lateinit var speciality : Speciality


}