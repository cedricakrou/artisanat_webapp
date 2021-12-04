package com.cedricakrou.artisanat.application.configuration.security.handler

import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.application.service.authentification_facade.AuthenticationFacade
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationSuccessHandler( private val authenticationFacade : AuthenticationFacade) : SimpleUrlAuthenticationSuccessHandler()  {

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        super.onAuthenticationSuccess(request, response, authentication)

        val user : Optional<User> = authenticationFacade.getAuthenticationUser()


        clearAuthenticationAttributes(request)

    }

}