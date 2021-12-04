package com.cedricakrou.artisanat.infrastructure.helper

import org.springframework.web.multipart.MultipartFile
import java.io.File

object InteractionServer
{
    const val addressImage : String = "/Users/mac/neo/"

    const val preAuth : String = addressImage + "preAuth/"

    fun saveImage( urlImage : String, image : MultipartFile,  nameImage : String ) : Boolean
    {
        var response : Boolean = true

        try
        {
            if (!File(urlImage).exists())
            {
                File (urlImage).mkdir();
            }

            val filePath: String = urlImage + nameImage
            val dest = File(filePath)
            image.transferTo(dest)
        }
        catch ( ex : Exception)
        {
            response = false
        }

        return response
    }
}