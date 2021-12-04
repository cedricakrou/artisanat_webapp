package com.cedricakrou.artisanat.domain.announcement.entity

import com.cedricakrou.artisanat.domain.account.entity.Client
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Announcement : BaseTableEntity() {

    var title : String = ""

    var description : String = ""

    @ManyToOne
    lateinit var client : Client

    var date : Date = Date()

}