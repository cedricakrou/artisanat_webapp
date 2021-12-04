package com.cedricakrou.artisanat.application.interceptor

import com.cedricakrou.artisanat.application.service.authentification_facade.AuthenticationFacade
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.entity.UserProfiler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.servlet.view.UrlBasedViewResolver
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationUserInterception(private val authenticationFacade: AuthenticationFacade) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return super.preHandle(request, response, handler)
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(request, response, handler, modelAndView)

        if ( modelAndView == null)
        {
            return
        }


        if ( modelAndView !is RedirectView && !modelAndView.viewName?.contains(UrlBasedViewResolver.REDIRECT_URL_PREFIX)!! ) {

            val user : Optional<User> = authenticationFacade.getAuthenticationUser()

            if ( user.isPresent ) {

                val userInjected = UserProfiler.profile(user = user.get())
                modelAndView.addObject( "user", userInjected)

            }

        }

    }
}