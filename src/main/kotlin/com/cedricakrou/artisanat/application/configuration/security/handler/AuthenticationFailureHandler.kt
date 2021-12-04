package com.cedricakrou.artisanat.application.configuration.security.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {


    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {

        val redirectUrl =  "/account/error"

        response!!.sendRedirect( redirectUrl )
    }

}