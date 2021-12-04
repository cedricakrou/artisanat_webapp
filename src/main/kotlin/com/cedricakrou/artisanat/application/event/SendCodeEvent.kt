package com.cedricakrou.artisanat.application.event

import com.cedricakrou.artisanat.application.event.dto.SendCodeDto
import org.springframework.context.ApplicationEvent

class SendCodeEvent( val sendCodeDto: SendCodeDto) : ApplicationEvent( sendCodeDto )