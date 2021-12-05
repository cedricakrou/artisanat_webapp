package com.cedricakrou.artisanat.domain.formation.entity

import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Formation( var name : String = "", var description : String ="" ) : BaseTableEntity() {

    @ManyToOne
    lateinit var artisan : Artisan

}