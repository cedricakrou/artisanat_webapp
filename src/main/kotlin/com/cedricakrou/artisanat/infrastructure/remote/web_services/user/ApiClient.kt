package com.cedricakrou.artisanat.infrastructure.remote.web_services.user

import com.b2i.neo.application.controlForm.Generate
import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.application.event.SendCodeEvent
import com.cedricakrou.artisanat.application.event.dto.SendCodeDto
import com.cedricakrou.artisanat.domain.account.entity.client.Client
import com.cedricakrou.artisanat.domain.account.entity.Role
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.secure_code.entity.SecureCode
import com.cedricakrou.artisanat.domain.secure_code.worker.SecureCodeDomain
import com.cedricakrou.artisanat.infrastructure.helper.secure_code.ISecureCode
import com.cedricakrou.artisanat.infrastructure.helper.sms.ISmsService
import com.cedricakrou.artisanat.infrastructure.helper.sms.aroli.SmsText
import com.cedricakrou.artisanat.infrastructure.remote.utils.ApiConst
import com.cedricakrou.artisanat.infrastructure.remote.utils.Response
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping( ApiConst.client )
class ApiClient(val roleDomain : RoleDomain,
                val userDomain: UserDomain,
                val secureCodeService : ISecureCode,
                val smsManager : ISmsService,
                val secureCodeDomain : SecureCodeDomain,
                private val eventPublisher: ApplicationEventPublisher
) {

    companion object {

        const val signIn = "sign-in"
        const val signUp = "sign-up"
        const val verifyOtp = "verify-otp"
        const val sendOtp = "send-otp"
    }


    /**
     * Cette Api sert à l'inscription du client à la plateforme
     */

    @PostMapping( signUp )
    fun signUp(@RequestParam("firstname") firstname: String,
               @RequestParam("lastname") lastname: String,
               @RequestParam("phoneNumber") phoneNumber: String,
               @RequestParam("username") username: String,
               @RequestParam("email") email: String,
               @RequestParam("password") password: String,
               @RequestParam( "confirmPassword" ) confirmPassword: String
    ) : Response<Nothing> {


        val response = Response<Nothing>()

        if (password != confirmPassword) {

            response.message = "Les mots de passes doivent être identiques"

        }
        else {

            val role : Role? =  roleDomain.findByName(UserType.CLIENT).orElse(null)

            if ( role == null ) {
                response.message  = "Le role Client est introuvable. Veuillez contacter le service client svp."
            }
            else {

                val client : Client = Client().apply {
                    this.firstname = firstname
                    this.lastname = lastname
                    this.phoneNumber = phoneNumber
                    this.username = username
                    this.email = email
                    this.password = password
                    this.roles = Collections.singleton(role)
                }


                val operationResult : OperationResult<User> = userDomain.save(client)
                val success : Boolean = ControlForm.VerifyApiHashMap(operationResult.errors)

                if ( success ) {

                    val code = Generate.generatedRandomNumber( "04", 10000 )
/**

                    Thread {
                        smsManager.sendSms(
                            to = client.phoneNumber,
                            content = SmsText.text( code )
                        )

                    }.start()

**/


                    Thread{

                        eventPublisher.publishEvent( SendCodeEvent(  SendCodeDto( operationResult.data, code )  ) )

                    }.start()

                    secureCodeDomain.save( SecureCode( client.username, code ) )

                    response.error = false
                }
                else {

                    response.message = ControlForm.extractFirstMessage(operationResult.errors)

                }

            }

        }


        return  response
    }


    /**
     * Cette Api sert à la connexion du client à la plateforme
     */

    @PostMapping( signIn )
    fun signIn(@RequestParam("username") username: String,
               @RequestParam("password") password: String) : Response<User> {

        val response = Response<User>()

        if ( username.isEmpty() ) {
            response.message  = "Veuillez renseigner l'username"
        }
        else if ( password.isEmpty() ) {
            response.message  = "Veuillez renseigner le mot de passe"
        }
        else {

            val client : User? = userDomain.findByUsername(username).orElse(null)

            if ( client == null || client !is Client) {

                response.message  = "L'utilisateur n'existe pas"
            }
            else {

                if ( BCryptPasswordEncoder().matches(password, client.password) ) {

                    if( !client.accountConfirmed ) {


                        val code = Generate.generatedRandomNumber( "04", 10000 )

                        /**

                        Thread {
                            smsManager.sendSms(
                                to = client.phoneNumber,
                                content = SmsText.text( code )
                            )

                        }.start()
                         **/


                        secureCodeDomain.save( SecureCode( client.username, code ) )

                        Thread{

                            eventPublisher.publishEvent( SendCodeEvent(  SendCodeDto( client, code )  ) )

                        }.start()


                    }


                    response.error = false
                    response.message  = "Connexion reussie"
                    response.data = client
                }
                else
                {
                    response.message  = "Authentification incorrecte. Veuillez verifier votre mot de passe ou votre username"
                }

            }

        }

        return response
    }


    @PostMapping( verifyOtp )
    fun verifyOtp(
        @RequestParam( "username" ) username: String,
        @RequestParam( "code" ) code : String
    ) : Response<SecureCode> = secureCodeService.authenticatedCodeSecure( username, code )

    @PostMapping( sendOtp )
    fun sendOtp( @RequestParam("username") username : String ) : Response<Nothing> =  secureCodeService.generateCodeSecure( username )


}
