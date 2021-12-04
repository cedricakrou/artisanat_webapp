package com.cedricakrou.artisanat.application.bootstrap

import com.cedricakrou.artisanat.domain.account.entity.Role
import com.cedricakrou.artisanat.domain.account.entity.UserType
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain


object RoleBootstrap {

    fun seed( roleDomain: RoleDomain) {

        if( roleDomain.count() == 0L ) {

            roleDomain.save( Role(UserType.ACTUATOR, UserType.ACTUATOR) )
            roleDomain.save( Role(UserType.ADMIN, UserType.ADMIN) )
            roleDomain.save( Role(UserType.CLIENT, UserType.CLIENT) )
            roleDomain.save( Role(UserType.ARTISAN, UserType.ARTISAN) )

            roleDomain.save( Role(UserType.SYSTEM_ARTISANAT, UserType.SYSTEM_ARTISANAT) )
        }

    }

}