package com.cedricakrou.artisanat.infrastructure.helper.sms.aroli

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties( ignoreUnknown = true )
class SmsResponseFormat(
    sender : String = "",
    receiver : String = "",
    message_id : String = "",
    message_count : Int = 1,
    network : String = ""
) {

    @JsonIgnore
    var status : StatusSmsResponse = StatusSmsResponse.ErrorEmptyData

}

sealed class StatusSmsResponse{

    object Success : StatusSmsResponse()
    object ErrorEmptyData : StatusSmsResponse()
    class ErrorServer( exception: Exception ) : StatusSmsResponse()

}