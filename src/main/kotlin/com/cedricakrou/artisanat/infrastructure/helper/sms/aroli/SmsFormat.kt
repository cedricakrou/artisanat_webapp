package com.cedricakrou.artisanat.infrastructure.helper.sms.aroli

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonSetter

class SmsFormat(var from: String,
                var to: String,
                var content: String,
) {

    var coding : Int = ListCoding.DEFAULT

    var dlr : String = Dlr.no

    private var dlrUrl : String = "http://host/dlr.php"
        @JsonGetter( "dlr-url" )  get() = field
        @JsonSetter("dlr-url") set(value ) { field = value }

    private var dlrLevel : Int = 3
        @JsonGetter( "dlr-level" )  get() = field
        @JsonSetter("dlr-level") set(value ) { field = value }

    private var dlrmethod : String = DlrMethod.POST
        @JsonGetter( "dlr-method" )  get() = field
        @JsonSetter("dlr-method") set(value ) { field = value }


}


object DlrMethod {
    const val POST = "POST"
    const val GET = "GET"
}

object Dlr{
    const val yes = "yes"
    const val no = "no"
}


object ListCoding {
    const val DEFAULT = 0
    const val LATIN1 = 3
    const val UCS2 = 8
}