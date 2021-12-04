package com.cedricakrou.artisanat.domain.common

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseTableEntity: AuditableEntity<String>() {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    open var id: Long=-1

    open var reference : String = ""

}