package com.cedricakrou.artisanat.application.configuration.security

import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImplementation( private val userDomain : UserDomain)  : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        return userDomain.findByUsername(username).orElseThrow { UsernameNotFoundException("User not found") }
    }

}