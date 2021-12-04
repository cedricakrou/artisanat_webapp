package com.cedricakrou.artisanat.application.event

import com.cedricakrou.artisanat.domain.account.entity.User
import org.springframework.context.ApplicationEvent

class SignUpStepOneEvent(val user : User) : ApplicationEvent( user )