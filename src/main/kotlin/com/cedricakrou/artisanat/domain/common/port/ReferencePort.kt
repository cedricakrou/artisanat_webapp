package com.b2i.neo.domain.common.port

import java.util.*

interface ReferencePort<T> {
    fun findByReference( reference : String ) : Optional<T>
    fun countAllByReference(reference: String ) : Long
}