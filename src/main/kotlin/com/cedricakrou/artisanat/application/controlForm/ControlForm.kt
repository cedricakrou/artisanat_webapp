package com.cedricakrou.artisanat.application.controlForm

import com.b2i.neo.application.controlForm.Color
import org.springframework.ui.Model
import org.springframework.web.servlet.mvc.support.RedirectAttributes

object ControlForm {
    fun model(model: Model, message: String, color: Color) {
        model.addAttribute("operationMessage", message)
        model.addAttribute("colorMessage", color)
    }

    fun modelPhoto(model: Model, clef: String, message: String, color: Color) {
        model.addAttribute(clef, message)
        model.addAttribute("colorMessage$clef", color)
    }

    fun redirectAttribute(redirectAttributes: RedirectAttributes, message: String, color: Color){

        redirectAttributes.addFlashAttribute("operationMessage", message)
        redirectAttributes.addFlashAttribute("colorMessage", color)
    }


    fun verifyHashMap(model: Model, errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isEmpty() )
        {
            model( model, "Operation réussie", Color.green )
        }
        else
        {
            val entry: Map.Entry<String, String> = errors.entries.iterator().next()
            val key = entry.key
            val value = entry.value

            success=false

            model(model, value, Color.red)
        }

        return success
    }


    fun verifyHashMapRedirect( redirectAttributes: RedirectAttributes, errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isEmpty() )
        {
            redirectAttribute( redirectAttributes, "Operation réussie", Color.green )
        }
        else
        {
            val entry: Map.Entry<String, String> = errors.entries.iterator().next()
            val key = entry.key
            val value = entry.value

            success=false

            redirectAttribute( redirectAttributes , value, Color.red)
        }

        return success
    }

    fun VerifyApiHashMap( errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isNotEmpty() )
        {
            success=false
        }

        return success
    }

    fun extractFirstMessage( errors: Map<String, String> ) : String {

        val entry: Map.Entry<String, String> = errors.entries.iterator().next()
        return entry.value
    }


}