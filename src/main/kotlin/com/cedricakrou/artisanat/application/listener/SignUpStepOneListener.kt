package com.cedricakrou.artisanat.application.listener

import com.cedricakrou.artisanat.application.event.SignUpStepOneEvent
import com.cedricakrou.artisanat.infrastructure.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SignUpStepOneListener( val emailHelper: EmailHelper) : ApplicationListener<SignUpStepOneEvent> {

    override fun onApplicationEvent(event: SignUpStepOneEvent) {

        val user = event.user

        if ( user.email.isNotEmpty()) {
            emailHelper.signUpStepOne( user )
        }

    }

}