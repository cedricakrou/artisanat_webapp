package com.b2i.neo.application.service.authentification_facade

import com.cedricakrou.artisanat.domain.account.entity.User
import java.util.*

interface IAuthenticationFacade {

    fun getAuthenticationUser() : Optional<User>

}