package com.b2i.neo.domain.common.port

import com.cedricakrou.artisanat.domain.common.OperationResult
import java.util.*

interface BasePortEmbebbedId<T> {

    fun save( model: T) : OperationResult<T>

    fun findById(  id : T ) : Optional<T>

    fun findAll() : List<T>

    fun deleteAll()

    fun deleteById( id : T )

    fun count() : Long

}