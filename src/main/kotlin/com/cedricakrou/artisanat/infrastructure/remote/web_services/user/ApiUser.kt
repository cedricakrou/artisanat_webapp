package com.cedricakrou.artisanat.infrastructure.remote.web_services.user

import com.cedricakrou.artisanat.infrastructure.remote.configuration.ApiConst
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping( ApiConst.user )
class ApiUser {

    @GetMapping( "sign-in" )
    fun signIn() : String = ""

}
