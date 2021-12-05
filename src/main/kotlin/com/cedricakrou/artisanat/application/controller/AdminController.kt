package com.cedricakrou.artisanat.application.controller

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.common.UrlCommon
import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.artisan.IManageArtisan
import com.cedricakrou.artisanat.domain.account.entity.client.Client
import com.cedricakrou.artisanat.domain.account.entity.client.IManageClient
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.cedricakrou.artisanat.domain.announcement.worker.AnnouncementDomain
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.domain.speciality.worker.SpecialityDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping( UrlCommon.adminEndPoint )
class AdminController(
    private val userDomain: UserDomain,
    private val verifyUser: VerifyUser,
    val artisanService : IManageArtisan,
    val specialityDomain: SpecialityDomain,
    val announcementDomain: AnnouncementDomain,
    val clientDomain : IManageClient
) : BaseController( UrlCommon.adminEndPoint ) {

    companion object {
        const val activateAccount = "/activate-account"
        const val artisans = "/artisans"
        const val specialities = "/specialities"
        const val saveSpeciality = "/save-speciality"
        const val deleteSpeciality = "/delete-speciality"
        const val clients = "/clients"

        const val changeStatutClient = "/change-statut-client"
        const val deleteClient  = "/delete-client"

        const val announcements = "/announcements"
        const val changeStatutAnnounce = "/change-statut-announce"
        const val deleteAnnounce  = "/delete-announce"
    }

    @GetMapping(UrlCommon.home)
    fun home() : String = forwardTo( UrlCommon.home )

    @GetMapping( artisans )
    fun artisans(
        model : Model
    ) : String {

        model.addAttribute( "artisans", artisanService.findAllByVisible( false )  )

        return forwardTo( artisans )
    }

    @PostMapping(activateAccount)
    fun activeAccount(
        redirectAttributes: RedirectAttributes,
        @RequestParam( "id" ) username : String
    ) : String {

        val user : User? = verifyUser.getUser( username )

        if ( user != null && user is Artisan) {

            user.visible = true

            userDomain.saveData( user )

            ControlForm.redirectAttribute( redirectAttributes, "Compte activé", Color.green  )


        }else {

            ControlForm.redirectAttribute( redirectAttributes, "Artisan introuvable", Color.red  )
        }

        return redirectTo( artisans)
    }

    /**
     * Speciality c
     */

    @GetMapping(specialities)
    fun specialities(
        model: Model
    ) : String {

        model.addAttribute( "specialities", specialityDomain.findAll() )

        return forwardTo( specialities )
    }

    @PostMapping( saveSpeciality )
    fun saveSpeciality(
        redirectAttributes: RedirectAttributes,
        @RequestParam("id") id : Long,
        @RequestParam("name") name : String,
        @RequestParam("description") description : String
    ) : String {

        val speciality = Speciality().apply {
            this.id = id
            this.name = name
            this.description = description
        }

        val operationResult = specialityDomain.save( speciality )

        if ( operationResult.errors.isEmpty() ) {
            ControlForm.redirectAttribute( redirectAttributes, "Secteur d'activité ajouté", Color.green )
        }
        else {
            ControlForm.redirectAttribute( redirectAttributes, ControlForm.extractFirstMessage( operationResult.errors ), Color.green )
        }

        return redirectTo( specialities )
    }


    @PostMapping(deleteSpeciality)
    fun deleteSpeciality(
        redirectAttributes: RedirectAttributes,
        @RequestParam( "id" ) id : Long
    ) : String {

        specialityDomain.deleteById(id)

        ControlForm.redirectAttribute( redirectAttributes, "Suppression effectué", Color.green )

        return redirectTo( specialities)
    }


    /**
     * Clients
     */

    @GetMapping( clients )
    fun clients(
        model: Model
    ) : String {

        model.addAttribute( "clients", clientDomain.findAllByDelete( false ) )

        return  forwardTo( clients)
    }

    @PostMapping( changeStatutClient )
    fun changeStatutClient(
        redirectAttributes: RedirectAttributes,
        @RequestParam( "id" ) username: String,
    ) : String {

        val user = verifyUser.getUser(username)

        if ( user == null  || user !is Client ) {

            ControlForm.redirectAttribute(
                redirectAttributes,
                "Client introuvaable",
                Color.red
            )

        }
        else {

            user.block = !user.block

            userDomain.saveData( user )

            ControlForm.redirectAttribute(
                redirectAttributes,
                if( !user.block ) "Compte debloqué" else "Compte bloqué",
                Color.green
            )

        }


        return redirectTo( clients )
    }


    @PostMapping( deleteClient )
    fun deleteClient(
        redirectAttributes: RedirectAttributes,
        @RequestParam( "id" ) username: String,
    ) : String {

        val user = verifyUser.getUser(username)

        if ( user == null  || user !is Client ) {

            ControlForm.redirectAttribute(
                redirectAttributes,
                "Client introuvaable",
                Color.red
            )

        }
        else {

            user.delete = true

            userDomain.saveData( user )

            ControlForm.redirectAttribute(
                redirectAttributes,
                "CLient supprimé",
                Color.green
            )

        }


        return redirectTo( clients )
    }


    /**
     * Announcements
     */

    @GetMapping( announcements )
    fun announcements(
        model: Model
    ) : String {

        model.addAttribute( "announcements", announcementDomain.findAllByDelete( delete = false )  )

        return forwardTo( announcements )
    }


    @GetMapping( changeStatutAnnounce + "/{id}" )
    fun changeStatutAnnonce(
        redirectAttributes: RedirectAttributes,
        @PathVariable( "id" ) id: Long,
    ) : String {

        val announcement : Announcement? = announcementDomain.findById(id).orElse(null)

        if ( announcement == null ) {

            ControlForm.redirectAttribute(
                redirectAttributes,
                "annonce introuvable",
                Color.red
            )

        }
        else {

            announcement.activate = !announcement.activate

            announcementDomain.save( announcement )

            ControlForm.redirectAttribute(
                redirectAttributes,
                if( !announcement.activate ) "Annonce desactivée" else "Annonce activée",
                Color.green
            )

        }


        return redirectTo( announcements )
    }


    @GetMapping( deleteAnnounce + "/{id}" )
    fun deleteAnnounce(
        redirectAttributes: RedirectAttributes,
        @PathVariable( "id" ) id: Long,
    ) : String {


        val announcement : Announcement? = announcementDomain.findById(id).orElse(null)

        if ( announcement == null ) {

            ControlForm.redirectAttribute(
                redirectAttributes,
                "Annonce introuvable",
                Color.red
            )

        }
        else {

            announcement.delete = true

            announcementDomain.save( announcement )

            ControlForm.redirectAttribute(
                redirectAttributes,
                "Annonce supprimé",
                Color.green
            )

        }


        return redirectTo( announcements )

    }

}