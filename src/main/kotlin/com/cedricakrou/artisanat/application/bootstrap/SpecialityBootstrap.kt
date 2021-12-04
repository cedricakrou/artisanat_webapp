package com.cedricakrou.artisanat.application.bootstrap

import com.cedricakrou.artisanat.domain.speciality.entity.ListSpeciality
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.domain.speciality.worker.SpecialityDomain


object SpecialityBootstrap {

    fun seed( specialityDomain : SpecialityDomain) {

        if ( specialityDomain.count() == 0L) {

            specialityDomain.save( Speciality( ListSpeciality.CARPENTER ) )
            specialityDomain.save( Speciality( ListSpeciality.CLEANER ) )
            specialityDomain.save( Speciality( ListSpeciality.DRESSMAKER ) )
            specialityDomain.save( Speciality( ListSpeciality.GARDENER ) )
            specialityDomain.save( Speciality( ListSpeciality.ELECTRICIAN ) )
            specialityDomain.save( Speciality( ListSpeciality.HOUSEHOLD ) )
            specialityDomain.save( Speciality( ListSpeciality.NURSE ) )
            specialityDomain.save( Speciality( ListSpeciality.PAINTER) )
            specialityDomain.save( Speciality( ListSpeciality.PLUMBER ) )
            specialityDomain.save( Speciality( ListSpeciality.TILER ) )
        }


    }

}