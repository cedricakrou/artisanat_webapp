package com.b2i.neo.application.controlForm

import java.util.*

object Generate {

    fun generatedRandomCharacter(targetLength: Int) : String {

        val leftLimit = 48 // numeral '0'
        val rightLimit = 122 // letter 'z'
        val random = Random()

        return random.ints(leftLimit, rightLimit + 1)
                .filter { i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97) }
                .limit(targetLength.toLong())
                .collect({ StringBuilder() }, java.lang.StringBuilder::appendCodePoint, java.lang.StringBuilder::append)
                .toString()
    }


    fun generatedRandomNumber( size : String, targetLimit: Int ) : String {

        val random = Random()
        return String.format("%${size}d", random.nextInt(targetLimit))
    }


}