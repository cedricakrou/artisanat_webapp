package com.cedricakrou.artisanat.domain.account.vm

import com.cedricakrou.artisanat.domain.account.entity.User


class UserVm() {

    var user : User? = null

    var oldPassword : String = "-1"
    var newPassword : String = ""
    var confirmPassword : String = ""

    var specialityID : Long = -1L
}