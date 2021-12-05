package com.cedricakrou.artisanat.application.controller

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.common.UrlCommon
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import com.cedricakrou.artisanat.domain.announcement.worker.AnnouncementDomain
import com.cedricakrou.artisanat.domain.experience.entity.Experience
import com.cedricakrou.artisanat.domain.experience.worker.ExperienceDomain
import com.cedricakrou.artisanat.domain.formation.entity.Formation
import com.cedricakrou.artisanat.domain.formation.worker.FormationDomain
import com.cedricakrou.artisanat.domain.reference.entity.Reference
import com.cedricakrou.artisanat.domain.reference.worker.ReferenceDomain
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping( UrlCommon.artisanEndPoint )
class ArtisanController(
    val roleDomain: RoleDomain,
    val userDomain: UserDomain,
    val verifyUser: VerifyUser,
    val announcementDomain: AnnouncementDomain,
    val formationDomain : FormationDomain,
    val experienceDomain : ExperienceDomain,
    val referenceDomain: ReferenceDomain,
    private val eventPublisher: ApplicationEventPublisher
) : BaseController( UrlCommon.artisanEndPoint ) {

    companion object {
        const val announcements = "/announcements"
        const val profil = "/profil"
        const val formations = "/formations"
        const val experiences = "/experiences"
        const val references = "/references"
    }

    @GetMapping(UrlCommon.home)
    fun home() : String = forwardTo( UrlCommon.home )

    /**
     *  To save and update Artisan
     */

    @GetMapping( announcements )
    fun listAnnouncements(
        model : Model
    ) : String {

        val user = verifyUser.getUserAuthenticated() as Artisan

        model.addAttribute( "announcements", announcementDomain.findAnnouncementsBySpeciality( user.speciality!!.id ) )

        return forwardTo( announcements )
    }

    @GetMapping( profil )
    fun profil(
        model: Model
    ) : String {

        val user : User = verifyUser.getUserAuthenticated()
        val artisan = user as Artisan

       model.addAttribute( "formations", artisan.formations  )
        model.addAttribute( "experiences", artisan.experiences )
        model.addAttribute( "references",  artisan.references)

       return forwardTo( profil )
    }

    @PostMapping( formations )
    fun formations(
        redirectAttributes: RedirectAttributes,
        @RequestParam("name") name : String,
        @RequestParam( "description" ) description : String
    ) : String {

        val artisan : User = verifyUser.getUserAuthenticated()

        val formation = Formation(
            name, description
        ).apply {
            this.artisan = artisan as Artisan
        }

        formationDomain.save( formation )

        ControlForm.redirectAttribute(
            redirectAttributes ,
            "Formation enregistrée",
            Color.green
        )

        return redirectTo( profil )
    }


    @PostMapping( experiences )
    fun experiences(
        redirectAttributes: RedirectAttributes,
        @RequestParam("startDate") startDate : String,
        @RequestParam("endDate") endDate : String,
        @RequestParam("name") name : String,
        @RequestParam("description") description: String
    ) : String {

        val artisan : User = verifyUser.getUserAuthenticated() as Artisan

        val experience = Experience(
            startDate = startDate,
            endDate = endDate,
            name = name,
            description = description
        ).apply {
            this.artisan = artisan as Artisan
        }

        experienceDomain.save( experience )

        ControlForm.redirectAttribute(
            redirectAttributes ,
            "Experience enregistrée",
            Color.green
        )

        return redirectTo( profil )
    }

    @PostMapping( references )
    fun references(
        redirectAttributes: RedirectAttributes,
        @RequestParam("name") name : String,
        @RequestParam( "description" ) description : String,
        @RequestParam( "link" ) link : String,
    ) : String {

        val artisan : User = verifyUser.getUserAuthenticated() as Artisan

        val reference = Reference(
            name, description, link
        ).apply {
            this.artisan = artisan as Artisan
        }

        referenceDomain.save( reference )

        ControlForm.redirectAttribute(
            redirectAttributes ,
            "Reference enregistrée",
            Color.green
        )

        return redirectTo( profil )
    }

}