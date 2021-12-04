package com.cedricakrou.artisanat.infrastructure.helper.sms

import com.cedricakrou.artisanat.infrastructure.helper.sms.aroli.Dlr
import com.cedricakrou.artisanat.infrastructure.helper.sms.aroli.DlrMethod
import com.cedricakrou.artisanat.infrastructure.helper.sms.aroli.ListCoding
import com.cedricakrou.artisanat.infrastructure.helper.sms.aroli.SmsResponseFormat

interface ISmsService {

    fun sendSms(
        from: String = "DJEPAY",
        to: String,
        content: String,
        coding: Int = ListCoding.DEFAULT,
        dlr: String = Dlr.no,
        dlrmethod: String = DlrMethod.POST,
        countryCode : String = "225"
    ) : SmsResponseFormat

}