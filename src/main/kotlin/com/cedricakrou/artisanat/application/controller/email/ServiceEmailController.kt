package com.cedricakrou.artisanat.application.controller.email

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.BaseController
import com.cedricakrou.artisanat.application.event.RegisterDoneEvent
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/email")
class ServiceEmailController(private val verifyUser: VerifyUser,
                             val eventPublisher: ApplicationEventPublisher) : BaseController("/service/email") {

    @GetMapping("/confirm-account/{username}")
    fun confirmEmail(
        redirectAttributes: RedirectAttributes,
        model : Model,
        @PathVariable("username") username : String ) : String {

        val user : User = verifyUser.getUser( username ) ?: return forwardTo("/failure-confirm-account")

        // envoie du mot de passe et du username pour la premiere connexion de l'utilisateur
/**
        Thread {

            eventPublisher.publishEvent( RegisterDoneEvent( user ) )

        }.start()
**/
        // envoie de sms pour le mot de passe

        ControlForm.redirectAttribute( redirectAttributes, "Compte Activé, Veuillez utiliser les identifiants du mail précedent pour vous connecter", Color.green )

        return redirectToZero( "/account", "login" )
    }
}