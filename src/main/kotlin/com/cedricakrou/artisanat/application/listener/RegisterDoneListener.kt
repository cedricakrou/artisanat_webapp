package com.cedricakrou.artisanat.application.listener

import com.cedricakrou.artisanat.infrastructure.helper.EmailHelper
import com.cedricakrou.artisanat.application.event.RegisterDoneEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class RegisterDoneListener( val emailHelper: EmailHelper) : ApplicationListener<RegisterDoneEvent> {

    override fun onApplicationEvent(event: RegisterDoneEvent) {

        val user = event.user

        if ( user.email.isNotEmpty()) {
            emailHelper.registerEmail( user )
        }

    }
}