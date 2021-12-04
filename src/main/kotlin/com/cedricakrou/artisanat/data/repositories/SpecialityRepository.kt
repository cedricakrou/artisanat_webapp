package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SpecialityRepository : JpaRepository<Speciality, Long> {

    fun findByName( name : String ) : Optional<Speciality>

    fun countByName( name : String ) : Long
}