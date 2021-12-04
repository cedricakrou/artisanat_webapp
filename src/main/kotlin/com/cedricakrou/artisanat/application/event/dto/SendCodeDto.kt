package com.cedricakrou.artisanat.application.event.dto

import com.cedricakrou.artisanat.domain.account.entity.User


data class SendCodeDto(val user: User, val code : String )