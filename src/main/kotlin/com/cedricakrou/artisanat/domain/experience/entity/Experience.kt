package com.cedricakrou.artisanat.domain.experience.entity

import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Experience( var name : String = "", var startDate : String, var endDate : String, var description : String ="" ) : BaseTableEntity() {

    @ManyToOne
    lateinit var artisan : Artisan

}