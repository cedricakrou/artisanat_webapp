package com.cedricakrou.artisanat.domain.speciality.port

import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.domain.common.port.BasePort
import java.util.*

interface IManageSpeciality : BasePort<Speciality> {
    fun findByName( name : String ) : Optional<Speciality>
}