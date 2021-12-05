package com.cedricakrou.artisanat.domain.account.entity

import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan


object UserProfiler {

    fun profile(user: User) = UserProfile(user)

    class UserProfile(user: User) {

        val id = user.id

        val firstname : String = user.firstname

        val lastname : String = user.lastname

        val username = user.username

        var function : String = ""

        var firstConnection : Boolean = true

        var actuator: Boolean = false

        var admin: Boolean = false

        var client : Boolean = false

        var artisan : Boolean = false


        var visible : Boolean = false

        init {
            val roles = user.roles

            function = roles!!.first().name

            actuator = roles.any { it.name == UserType.ACTUATOR }
            admin = roles.any { it.name == UserType.ADMIN }
            client = roles.any { it.name == UserType.CLIENT }
            artisan = roles.any { it.name == UserType.ARTISAN }

            if ( user is Artisan ) {
                visible = user.visible
            }

            firstConnection = user.firstConnection

        }
    }
}