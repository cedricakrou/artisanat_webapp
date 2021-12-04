package com.cedricakrou.artisanat.infrastructure.helper.sms.aroli


object SmsText{

    fun text( code : String ) =
        """
                $code est votre code OTP qui servira à valider votre opération.
                Ce code est valable pour une durée de 5 minutes.
            """.trimIndent()

    fun returnText( success: Boolean ) : String =
        if ( success ) "Votre paiement a été effectuée avec succès." else "votre paiement a echoué."
}