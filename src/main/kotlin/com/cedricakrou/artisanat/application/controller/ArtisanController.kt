package com.cedricakrou.artisanat.application.controller

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controller.common.UrlCommon
import com.cedricakrou.artisanat.application.event.SignUpStepOneEvent
import com.cedricakrou.artisanat.domain.account.entity.Artisan
import com.cedricakrou.artisanat.domain.account.entity.Role
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.infrastructure.helper.InteractionServer
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
@RequestMapping( UrlCommon.artisanEndPoint )
class ArtisanController(
    val roleDomain: RoleDomain,
    val userDomain: UserDomain,
    private val eventPublisher: ApplicationEventPublisher
) : BaseController( UrlCommon.artisanEndPoint ) {

    companion object {
        const val signUpSuccess = "sign-up-success"

    }

    @GetMapping(UrlCommon.home)
    fun home() : String = forwardTo( UrlCommon.home )

    /**
     *  To save and update Artisan
     */

    @GetMapping( UrlCommon.save )
    fun save(
        redirectAttributes: RedirectAttributes,
        @RequestParam("firstname") firstname : String,
        @RequestParam("lastname") lastname : String,
        @RequestParam("email") email : String,
        @RequestParam("username") username : String,
        @RequestParam("phone1") phone1 : String,
        @RequestParam("phone2") phone2 : String,
        @RequestParam("biography") biography : String,
        @RequestParam("speciality") speciality : Long,
        @RequestParam("cniRecto") cniRecto : MultipartFile,
        @RequestParam("cniVerso") cniVerso : MultipartFile,
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
                this.phoneNumber1 = phone1
                this.phoneNumber2 = phone2
                this.biography = biography
                this.speciality = Speciality().apply { id = speciality }
            }

            val operationResult: OperationResult<User> = userDomain.save( user )

            val success: Boolean = ControlForm.verifyHashMapRedirect( redirectAttributes, operationResult.errors)

            if (!success) {
                return redirectTo( UrlCommon.save )
            }
            else {

                // envoi d'email pour la confirmation de l'inscription

                Thread {
                    eventPublisher.publishEvent( SignUpStepOneEvent( user ) )
                }.start()



                // save cni imag
                // ça ne doit pas être un point bloquant
                if (cniRecto.originalFilename!!.isNotEmpty() && cniVerso.originalFilename!!.isNotEmpty()  ) {

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


        return forwardTo(  UrlCommon.save )
    }
}