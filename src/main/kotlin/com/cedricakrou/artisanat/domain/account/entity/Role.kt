package com.cedricakrou.artisanat.domain.account.entity

import com.cedricakrou.artisanat.domain.common.BaseTableEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(indexes = [Index(name = "i_role_name", columnList = "name", unique = true)])
class Role : BaseTableEntity, Serializable {

//    @Column(length = Constant.JPA_CONSTRAINTS_MEDIUM)
    var name: String = ""

    var libelle: String = ""

    constructor() {}

    constructor(name: String)  {
        this.name = name
    }

    constructor(name: String, libelle: String) {
        this.name = name
        this.libelle = libelle
    }
}
