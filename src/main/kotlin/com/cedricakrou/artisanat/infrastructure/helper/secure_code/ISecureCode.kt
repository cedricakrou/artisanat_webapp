package com.cedricakrou.artisanat.infrastructure.helper.secure_code

import com.cedricakrou.artisanat.domain.secure_code.entity.SecureCode
import com.cedricakrou.artisanat.infrastructure.remote.utils.Response


interface ISecureCode {

    fun verifyOtp(memberNo : String, code : String )  : Response<SecureCode>

    /**
     * Cette fonction permet de générer le code Otp
     */
    fun generateCodeSecure( username : String) : Response<Nothing>

    /**
     * Cette fonction permet d'authentifier le code otp
     * de verifier la durée et si le code à dejà été utilisé
     */
    fun authenticatedCodeSecure(memberNo : String, code: String ) : Response<SecureCode>

}