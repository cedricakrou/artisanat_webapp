package com.cedricakrou.artisanat.application.service.authentification_facade

import com.b2i.neo.application.service.authentification_facade.IAuthenticationFacade
import com.cedricakrou.artisanat.data.repositories.UserRepository
import com.cedricakrou.artisanat.domain.account.entity.User
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthenticationFacade( private val userRepository : UserRepository) : IAuthenticationFacade {

    override fun getAuthenticationUser() : Optional<User> {

        var user : Optional<User> = Optional.empty()

        val authentication : Authentication? = SecurityContextHolder.getContext().authentication

        if ( authentication != null && authentication !is AnonymousAuthenticationToken ) {

            user = userRepository.findByUsername(authentication.name)

        }

        return user
    }

}