package com.cedricakrou.artisanat.application.controller.account

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.BaseController
import com.cedricakrou.artisanat.application.controller.common.UrlCommon
import com.cedricakrou.artisanat.application.event.SignUpStepOneEvent
import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.account.entity.Role
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.domain.speciality.worker.SpecialityDomain
import com.cedricakrou.artisanat.infrastructure.helper.InteractionServer
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/account")
class AccountController(val verifyUser: VerifyUser,
                        val specialityDomain: SpecialityDomain,
                        val userDomain: UserDomain,
                        val roleDomain: RoleDomain,
                        private val eventPublisher: ApplicationEventPublisher
) : BaseController("/account")  {

    companion object {
        const val signUpSuccess = "/sign-up-success"
        const val signUp = "/sign-up"
    }


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


    @GetMapping( signUp )
    fun signUp(
        model: Model
    ) : String {

        model.addAttribute( "specialities", specialityDomain.findAll() )

        return forwardTo( signUp )
    }


    // save artisan

    @PostMapping( UrlCommon.save )
    fun saveArtisan(
        redirectAttributes: RedirectAttributes,
        @RequestParam("firstname") firstname : String,
        @RequestParam("lastname") lastname : String,
        @RequestParam("email") email : String,
        @RequestParam("username") username : String,
        @RequestParam("phone1") phone1 : String,
        @RequestParam("phone2") phone2 : String,
        @RequestParam("biography") biography : String,
        @RequestParam("speciality") speciality : Long,
        @RequestParam("cniRecto", required = false) cniRecto : MultipartFile? = null,
        @RequestParam("cniVerso") cniVerso : MultipartFile? = null,
    ) : String {


        // ge role
        val role : Role = roleDomain.findByName(UserType.ARTISAN).get()

        // create user
        var user : Artisan = Artisan(
            username = username,
            password = "",
            roles = Collections.singleton(role)
        ).apply {
            this.firstname = firstname
            this.lastname = lastname
            this.email = email
            this.phoneNumber = phone1
            this.phoneNumber1 = phone1
            this.phoneNumber2 = phone2
            this.biography = biography
            this.speciality = Speciality().apply { id = speciality }
        }

        val operationResult: OperationResult<User> = userDomain.save( user )

        val success: Boolean = ControlForm.verifyHashMapRedirect( redirectAttributes, operationResult.errors)

        if (!success) {

            ControlForm.redirectAttribute( redirectAttributes, ControlForm.extractFirstMessage( operationResult.errors ), Color.red )

            return redirectTo( signUp )
        }
        else {

            // envoi d'email pour la confirmation de l'inscription

            Thread {
                eventPublisher.publishEvent( SignUpStepOneEvent( user ) )
            }.start()



            // save cni imag
            // ça ne doit pas être un point bloquant
            if ( cniRecto != null && cniVerso != null && cniRecto!!.originalFilename!!.isNotEmpty() && cniVerso!!.originalFilename!!.isNotEmpty()  ) {

                try {

                    // save cni recto

                    if (!InteractionServer.saveImage(
                            InteractionServer.cniRecto,
                            cniRecto,
                            cniRecto.originalFilename!!
                        )  ||

                        !InteractionServer.saveImage(
                            InteractionServer.cniVerso,
                            cniVerso,
                            cniVerso.originalFilename!!
                        )


                    ) {
                        ControlForm.redirectAttribute(
                            redirectAttributes,
                            "Echec de la sauvegarde de la pièce jointe",
                            Color.red
                        )

                    } else {

                        user = operationResult.data as Artisan

                        user.cniRecto = cniRecto.originalFilename!!
                        user.cniVerso = cniVerso.originalFilename!!

                        userDomain.saveData( user )
                    }


                } catch (ex: Exception) {

                    ControlForm.redirectAttribute(
                        redirectAttributes,
                        "Echec de la sauvegarde de la pièce jointe",
                        Color.red
                    )
                }


            }


        }


        return forwardTo( signUpSuccess )
    }


}