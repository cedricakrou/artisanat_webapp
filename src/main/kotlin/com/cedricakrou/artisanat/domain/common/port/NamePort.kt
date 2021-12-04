package com.b2i.neo.domain.common.port

import java.util.*

interface NamePort<T> {
    fun findByName( name : String ) : Optional<T>
    fun countByName( name : String ) : Long
}