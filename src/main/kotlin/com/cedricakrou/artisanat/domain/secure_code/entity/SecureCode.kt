package com.cedricakrou.artisanat.domain.secure_code.entity

import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class SecureCode(val memberNo : String,
                 val code : String,
                 val date : LocalDateTime = LocalDateTime.now()
) : BaseTableEntity() {

    @Column( columnDefinition = "boolean default false" )
    var use : Boolean = false
}