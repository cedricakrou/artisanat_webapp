package com.cedricakrou.artisanat.application.controller

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.common.UrlCommon
import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.artisan.IManageArtisan
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.domain.speciality.worker.SpecialityDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping( UrlCommon.adminEndPoint )
class AdminController(
    private val userDomain: UserDomain,
    private val verifyUser: VerifyUser,
    val artisanService : IManageArtisan,
    val specialityDomain: SpecialityDomain
) : BaseController( UrlCommon.adminEndPoint ) {

    companion object {
        const val activateAccount = "/activate-account"
        const val artisans = "/artisans"
        const val specialities = "/specialities"
        const val saveSpeciality = "/save-speciality"
        const val deleteSpeciality = "/delete-speciality"
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

}