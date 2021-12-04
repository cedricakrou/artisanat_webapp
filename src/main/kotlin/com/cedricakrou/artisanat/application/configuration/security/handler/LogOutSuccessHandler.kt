package com.cedricakrou.artisanat.application.configuration.security.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogOutSuccessHandler : SimpleUrlLogoutSuccessHandler() {

    override fun onLogoutSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        super.onLogoutSuccess(request, response, authentication)
    }

}