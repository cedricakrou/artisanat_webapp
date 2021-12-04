package com.cedricakrou.artisanat.infrastructure.remote.utils

class Response<T>( var message : String = "", var error : Boolean = true, var data : T? = null )