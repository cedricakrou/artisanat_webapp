package com.cedricakrou.artisanat.infrastructure.helper.secure_code

import com.b2i.neo.application.controlForm.Generate
import com.cedricakrou.artisanat.application.event.dto.SendCodeDto
import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.event.SendCodeEvent
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.secure_code.entity.SecureCode
import com.cedricakrou.artisanat.domain.secure_code.worker.SecureCodeDomain
import com.cedricakrou.artisanat.infrastructure.remote.utils.Response
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class SecureCodeService(val secureCodeDomain: SecureCodeDomain,
                        val verifyUser: VerifyUser,
                        private val eventPublisher: ApplicationEventPublisher
                         ) : ISecureCode
{

    override fun verifyOtp(memberNo : String, code : String )  : Response<SecureCode> {


        val response = Response<SecureCode>()

        if ( memberNo.isNotEmpty() ) {

            if ( code.isNotEmpty() ) {

                val secureCode : SecureCode? = secureCodeDomain.findByMemberNoAndCode(memberNo, code).orElse( null )

                if ( secureCode != null ) {

                    val duration : Duration = Duration.between( secureCode.date, LocalDateTime.now() )

                    val min = duration.toMinutes()

                    if ( min <= 5 && !secureCode.use ) {

                        response.error = false

                        response.message = "Code verifié avec succès"

                    }
                    else {
                        response.message = "Code desactivé. Veuillez générer un nouveau code."
                    }

                }
                else {
                    response.message = "Accès au service non autorisé (code introuvable)."
                }

            }
            else {
                response.message = "Veuillez saisir le code envoyé au client."
            }

        }else {
            response.message = "Veuillez saisir le numéro du client."
        }

        return response
    }

    override fun generateCodeSecure(username : String): Response<Nothing> {

        val response : Response<Nothing> = Response()

        val user : User? = verifyUser.getUser( username )

        if ( user == null ) {
            response.message = "Utilisateur introuvable"
        }
        else {

            try {

                val code = Generate.generatedRandomNumber( "04", 10000 )

                /**
                Thread {

                smsManager.sendSms(
                to = "225${user.phoneNumber}",
                content = SmsText.text( code )
                )

                }.start()
                 **/

                secureCodeDomain.save( SecureCode( user.username, code ) )

                Thread{

                    eventPublisher.publishEvent( SendCodeEvent(  SendCodeDto( user, code )  ) )

                }.start()


                response.error = false
                response.message = "Code otp généré. Veuillez consulter vos mails & messages"
            }
            catch ( ex : Exception ) {
                response.message = "Erreur serveur, veuillez réesayer plus tard"
            }

        }




        return  response
    }

    override fun authenticatedCodeSecure(memberNo : String, code: String): Response<SecureCode> {

        val response : Response<SecureCode> = Response<SecureCode>()

        if (code.isNotEmpty() || memberNo.isNotEmpty()) {

            // on verfie si le code otp associé au member no existe en base

            var secureCode: SecureCode? = secureCodeDomain.findByMemberNoAndCode(memberNo, code).orElse(null)

            if (secureCode != null) {

                // on verifie la date et la durée du code otp

                val duration: Duration = Duration.between(secureCode.date, LocalDateTime.now())

                val min = duration.toMinutes()

                // si la code a une date inferieure à 5 minutes et il n'a pas encore utilisé
                // alors on le valide

                if (min <= 5 && !secureCode.use) {

                    response.error = false
                    response.data = secureCode

                    secureCode.use = true

                    secureCodeDomain.save( secureCode )

                } else {

                    response.message =
                        "Code desactivé ou déjà utilisé . Veuillez générer un nouveau code."

                }

            }
            else {

                response.message =
                    "Accès au service non autorisé (code introuvable)."

            }

        }
        else {
            response.message = "Veuillez renseigner le code secure ou le code Otp"
        }

        return response
    }

}