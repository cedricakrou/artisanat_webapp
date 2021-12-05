package com.cedricakrou.artisanat.application.controller

import com.cedricakrou.artisanat.application.controlForm.VerifyUser
import com.cedricakrou.artisanat.application.controller.common.UrlCommon
import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import com.cedricakrou.artisanat.domain.announcement.worker.AnnouncementDomain
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping( UrlCommon.artisanEndPoint )
class ArtisanController(
    val roleDomain: RoleDomain,
    val userDomain: UserDomain,
    val verifyUser: VerifyUser,
    val announcementDomain: AnnouncementDomain,
    private val eventPublisher: ApplicationEventPublisher
) : BaseController( UrlCommon.artisanEndPoint ) {

    companion object {
        const val announcements = "/announcements"
    }

    @GetMapping(UrlCommon.home)
    fun home() : String = forwardTo( UrlCommon.home )

    /**
     *  To save and update Artisan
     */

    @GetMapping( announcements )
    fun listAnnouncements(
        model : Model
    ) : String {

        val user = verifyUser.getUserAuthenticated() as Artisan

        model.addAttribute( "announcements", announcementDomain.findAnnouncementsBySpeciality( user.speciality!!.id ) )

        return forwardTo( announcements )
    }
}