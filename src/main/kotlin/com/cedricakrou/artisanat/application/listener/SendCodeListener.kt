package com.cedricakrou.artisanat.application.listener

import com.cedricakrou.artisanat.application.event.SendCodeEvent
import com.cedricakrou.artisanat.infrastructure.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SendCodeListener( val emailHelper: EmailHelper) : ApplicationListener<SendCodeEvent> {

    override fun onApplicationEvent(event: SendCodeEvent) {

        val user = event.sendCodeDto.user

        if ( user.email.isNotEmpty() ) {
            emailHelper.sendCodeEmail( event.sendCodeDto )
        }

    }
}