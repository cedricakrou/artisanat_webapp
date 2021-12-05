package com.cedricakrou.artisanat.infrastructure.remote.web_services.annonce

import com.cedricakrou.artisanat.application.controlForm.ControlForm
import com.cedricakrou.artisanat.domain.account.entity.client.Client
import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.cedricakrou.artisanat.domain.announcement.worker.AnnouncementDomain
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import com.cedricakrou.artisanat.infrastructure.remote.utils.ApiConst
import com.cedricakrou.artisanat.infrastructure.remote.utils.Response
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping( ApiConst.announcement )
class ApiAnnouncement(
    val announcementDomain: AnnouncementDomain
) {

    companion object {
        const val save = "/save"

        const val myAnnouncements = "/my-announcements"
    }

    @PostMapping( save )
    fun save(
        @RequestParam("title") title : String,
        @RequestParam("description") description : String,
        @RequestParam("price") price : String,
        @RequestParam("client") client : String,
        @RequestParam("speciality") speciality : Long
    ) : Response<Announcement> {

        val response = Response<Announcement>()

        val announcement : Announcement = Announcement().apply {
            this.title = title
            this.description = description
            this.price = price.toDouble()
            this.client = Client().apply { username = client }
            this.speciality = Speciality().apply { id = speciality }
        }

        val operationResult : OperationResult<Announcement> = announcementDomain.save( announcement )

        if ( operationResult.errors.isEmpty() ) {
            response.error = false
            response.message = "Votre annonce a été crée avec succès"
            response.data = operationResult.data
        }
        else {
            response.message = ControlForm.extractFirstMessage(operationResult.errors)
        }

        return response
    }

    @PostMapping( myAnnouncements )
    fun myAnnouncements(
        @RequestParam("username") username : String
    ) : Response<List<Announcement>> =
        Response<List<Announcement>>().apply {
            error = false
            data = announcementDomain.findMyAnnouncements( username )
        }

}