package com.cedricakrou.artisanat.application.controller.account

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.BaseController
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/account")
class AccountController(val verifyUser: VerifyUser,
                        val userDomain: UserDomain
) : BaseController("/account")  {

    @GetMapping(value = ["", "/login"])
    internal fun login(
            model: Model,
            redirectAttributes: RedirectAttributes,
//            device: Device,
            request: HttpServletRequest,
            @RequestParam("error", required = false) error: String?,
            @RequestParam("username", required = false) username : String?,
            @RequestParam("logout", required = false) logout : String?
    ) : String {

        if ( error != null){

            return redirectTo( "error" )
        }

        if ( logout != null) {
            return redirectTo( "login" )
        }

//        request.session.setAttribute("device", device)

//        ControlForm.model( model , "Erreur lors de la connexion: username ou password incorrect", Color.red )


        return forwardTo("/login")
    }

    @GetMapping(value = ["error"])
    fun error( model: Model ) : String {

        ControlForm.model( model, "Username ou password incorrect", Color.red )

        return forwardTo("/login")
    }
}