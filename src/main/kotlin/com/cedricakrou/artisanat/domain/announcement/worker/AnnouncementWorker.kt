package com.cedricakrou.artisanat.domain.announcement.worker

import com.b2i.neo.application.controlForm.Generate
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.data.repositories.AnnouncementRepository
import com.cedricakrou.artisanat.domain.account.entity.client.Client
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.domain.speciality.worker.SpecialityDomain
import org.springframework.stereotype.Service
import java.util.*

@Service
class AnnouncementWorker( val repository : AnnouncementRepository,
                          val specialityDomain : SpecialityDomain,
                          val verifyUser: VerifyUser
) : AnnouncementDomain {

    override fun save(model: Announcement): OperationResult<Announcement> {

        val errors : HashMap<String, String> = HashMap<String, String>()

        var announcement : Announcement = model

        if ( model.title.isEmpty() ) {
            errors["titleEmpty"] = "Veuillez renseigner un titre"
        }

        if( model.description.isEmpty() ) {
            errors["descriptionEmpty"] = "Veuillez renseigner la description du projet"
        }

        if ( model.price <= 0.0  ) {
            errors["priceIncorrect"] = "Veuillez saisir un prix correct svp"
        }

        // get speciality
        val speciality = specialityDomain.findById( id = model.speciality.id ).orElse( null )

        if ( speciality == null ) {
            errors["specialityNotExist"] = "Veuillez renseigner la specialité svp"
        }
        else {
            model.speciality = speciality
        }

        // get client


        val client : User? = verifyUser.getUser( username = model.client.username )

        if ( client == null || client !is Client) {
            errors["clientNotExist"] = "Le clientt n'existe pas"
        }
        else {
            model.client = client
        }

        if ( errors.isEmpty() ) {

            model.reference =  Generate.generatedRandomCharacter( 5 )  + "A-" + String.format("%03d", count() + 1 )

            announcement = repository.save( model )
        }

        return OperationResult( errors, announcement )

    }

    override fun findById(id: Long): Optional<Announcement> = repository.findById( id )

    override fun findAll(): List<Announcement> = repository.findAll()

    override fun deleteAll() = repository.deleteAll()

    override fun deleteById(id: Long) = repository.deleteById( id )

    override fun count(): Long = repository.count()

    override fun findMyAnnouncements(username: String): List<Announcement>  = repository.findAllByClient_Username(username)

    override fun findAnnouncementsBySpeciality( id : Long ): List<Announcement> = repository.findAllBySpeciality_IdAndDelete( id, false )

    override fun findAllByDelete(delete: Boolean): List<Announcement> = repository.findAllByDelete(delete)

}