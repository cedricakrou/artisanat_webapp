package com.cedricakrou.artisanat.application.controller

import com.b2i.neo.application.controlForm.Color
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/backend")
class MainController(private val userDomain: UserDomain,
                     private val verifyUser: VerifyUser,
) : BaseController( "/backend" )  {

    companion object {
        const val baseBackendTemplate = "/backend"
        const val baseAccountTemplate = "/general"
        const val changePassword = "/change-password"


        const val verifyOtp = "/verify-otp"
    }



    @GetMapping(value = [ "", "/" ])
    fun home( model : Model ) : String
    {
        val user : User = verifyUser.getUserAuthenticated()
        val userType : String = verifyUser.getUserType( user )

        if ( user.firstConnection ) {
            return redirectTo( changePassword )
        }
        else {

           return when( userType ){

            UserType.ACTUATOR -> {
                redirectTo("/actuator/home")
            }

            UserType.ADMIN -> {
                redirectTo("/admin/artisans")
            }

            UserType.ARTISAN -> {
                redirectTo( "/artisan/profil" )
            }

            UserType.CLIENT -> {
                redirectTo( "/client/home" )
            }

               else -> redirectTo( "home" )
           }

        }
    }


    @GetMapping( changePassword )
    fun getChangePassword() : String {
        return forwardToPage( baseAccountTemplate, changePassword )
    }
    
    @PostMapping( changePassword )
    fun postChangePassword(redirectAttributes: RedirectAttributes,
                           @RequestParam("password") password : String,
                           @RequestParam("confirm-password") confirmPassword : String
    ) : String {
        
        if ( password.isEmpty() ) {
            ControlForm.redirectAttribute( redirectAttributes, "Veuillez saisir votre mot de passe", Color.red )
        }
        else {

            if ( password != confirmPassword ){
                ControlForm.redirectAttribute( redirectAttributes, "Les mots de passes doivent être identiques", Color.red )
            }
            else {

                val user : User = verifyUser.getUserAuthenticated()

                user.password = BCryptPasswordEncoder().encode( password )
                user.firstConnection = false

                userDomain.save( user )

                return redirectTo( "" )
            }

        }

        return redirectTo( changePassword )
    }

    @GetMapping( verifyOtp )
    fun verifyOtp(
    ) : String {

        return forwardTo("/common$verifyOtp")
    }

}