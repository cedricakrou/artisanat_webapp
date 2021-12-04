package com.cedricakrou.artisanat.application.controlForm


import com.cedricakrou.artisanat.application.service.authentification_facade.AuthenticationFacade
import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import org.springframework.stereotype.Service


@Service
class VerifyUser(private val userManager: UserDomain,
                 private val authenticationFacade: AuthenticationFacade
) {

    fun userExistsByUsername(pseudo: String): Boolean {
        var exist: Boolean = false

        val user: User? = userManager.findByUsername(pseudo).orElse(null)

        if (user != null) {
            exist = true
        }

        return exist
    }


    fun userExistsByPhone(pseudo: String): Boolean {
        var exist: Boolean = false

        val user: User? = userManager.findByUsername(pseudo).orElse(null)

        if (user != null) {
            exist = true
        }

        return exist
    }

    fun userExistsByEmail(email: String): Boolean {
        var exist: Boolean = false

        val user: User? = userManager.findByEmail(email).orElse(null)

        if (user != null) {
            exist = true
        }

        return exist
    }


    fun getUser( username : String ) : User? {

        return userManager.findByUsername(username).orElse(null)
    }

    fun getUserAuthenticated() : User {
        return authenticationFacade.getAuthenticationUser().get()
    }

    fun getUserType( user : User) : String {
        return userManager.findTypeBy(user.id)
    }
}