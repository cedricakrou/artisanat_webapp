package com.cedricakrou.artisanat.application.controller

abstract class BaseController( private val templateBaseDir : String, private val routeBase : String = templateBaseDir ) {

    internal fun forwardTo( templatePath: String ) : String
    {
        return String.format("%s%s", templateBaseDir, templatePath  )
    }

    internal fun redirectTo( urlController : String ) : String
    {
        return String.format("redirect:%s%s", routeBase, urlController )
    }

    internal fun redirectFromZero(baseUrl : String, urlController : String ) : String
    {
        return String.format("redirect:%s%s", baseUrl, urlController )
    }

    internal fun forwardToPage( baseUrl : String,  templatePath: String ) : String
    {
        return String.format("%s%s%s", templateBaseDir, baseUrl, templatePath )
    }

    internal fun forwardToPageByZero( baseUrl : String,  templatePath: String ) : String
    {
        return String.format("%s%s",  baseUrl, templatePath )
    }
}