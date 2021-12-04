package com.cedricakrou.artisanat.application.controller

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.common.UrlCommon
import com.cedricakrou.artisanat.domain.account.entity.Artisan
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping( UrlCommon.adminEndPoint )
class AdminController(
    private val userDomain: UserDomain,
    private val verifyUser: VerifyUser
) : BaseController( UrlCommon.adminEndPoint ) {

    companion object {
        const val activateAccount = "/activate-account"
    }

    @GetMapping(UrlCommon.home)
    fun home() : String = forwardTo( UrlCommon.home )

    @GetMapping(activateAccount)
    fun activeAccount(
        redirectAttributes: RedirectAttributes,
        @RequestParam( "username" ) username : String
    ) : String {

        val user : User? = verifyUser.getUser( username )

        if ( user != null && user is Artisan ) {

            user.visible = true

            userDomain.saveData( user )

            ControlForm.redirectAttribute( redirectAttributes, "Artisan introuvable", Color.green  )

        }else {

            ControlForm.redirectAttribute( redirectAttributes, "Artisan introuvable", Color.red  )
        }

        return redirectTo( UrlCommon.home )
    }
}