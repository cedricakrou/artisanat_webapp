package com.cedricakrou.artisanat.application.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("")
class HomeController : BaseController("") {

    @GetMapping( value = ["", "/"] )
    fun home() : String {

        return redirectFromZero( "/account", "sign-up" )
    }

}