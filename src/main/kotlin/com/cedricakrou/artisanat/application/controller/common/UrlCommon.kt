package com.cedricakrou.artisanat.application.controller.common

object UrlCommon {

    const val home = "/home"

    const val backendEndPoint : String = "/backend/"

    const val artisanEndPoint : String = "${com.cedricakrou.artisanat.application.controller.common.UrlCommon.backendEndPoint}artisan"
    const val adminEndPoint : String = "${com.cedricakrou.artisanat.application.controller.common.UrlCommon.backendEndPoint}admin"

    const val save = "/save"
}