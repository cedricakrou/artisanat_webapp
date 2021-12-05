package com.cedricakrou.artisanat.domain.reference.entity

import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Reference( var name : String = "", var description : String ="", var link : String = "" ) : BaseTableEntity() {

    @ManyToOne
    lateinit var artisan : Artisan

}