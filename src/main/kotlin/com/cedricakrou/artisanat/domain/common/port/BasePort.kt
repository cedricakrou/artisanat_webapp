package com.cedricakrou.artisanat.domain.common.port

import com.cedricakrou.artisanat.domain.common.OperationResult
import java.util.*

interface BasePort<T> {

    fun save( model: T) : OperationResult<T>

    fun findById(  id : Long ) : Optional<T>

    fun findAll() : List<T>

    fun deleteAll()

    fun deleteById( id : Long )

    fun count() : Long

}