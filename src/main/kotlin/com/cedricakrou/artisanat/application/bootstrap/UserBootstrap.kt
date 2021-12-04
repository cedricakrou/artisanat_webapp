package com.cedricakrou.artisanat.application.bootstrap
import com.cedricakrou.artisanat.domain.account.entity.Actuator
import com.cedricakrou.artisanat.domain.account.entity.Admin
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import java.util.*

object UserBootstrap {

    fun seed(userDomain: UserDomain, roleDomain: RoleDomain) {

        if (userDomain.count() == 0L) {


            // actuator
            roleDomain.findByName(UserType.ACTUATOR).ifPresent { role ->

                val actuator = Actuator("actuator", "cekario", Collections.singleton(role))
                actuator.firstname = "actuator"
                actuator.lastname = "actuator"
                actuator.email = "actuator@gmail.com"
                actuator.phoneNumber = "01020304"

                userDomain.save(actuator)

            }


            // admin
            roleDomain.findByName(UserType.ADMIN).ifPresent { role ->

                val user = Admin("admin", "open", Collections.singleton(role))
                user.firstname = "admin"
                user.lastname = "admin"
                user.phoneNumber = "01020305"
                user.email = "client@gmail.com"

                userDomain.save(user)

            }

        }
    }
}