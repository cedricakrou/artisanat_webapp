package com.cedricakrou.artisanat.domain.common

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

@MappedSuperclass
@EntityListeners(EntityListeners::class)
abstract class AuditableEntity<U> {

    @CreatedBy
    open var createdBy : U? = null

    @LastModifiedBy
    open var lastModifiedBy : U? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate : Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate : Date? = null

}