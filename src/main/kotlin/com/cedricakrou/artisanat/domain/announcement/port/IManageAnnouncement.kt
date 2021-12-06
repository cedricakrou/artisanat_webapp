package com.cedricakrou.artisanat.domain.announcement.port

import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.cedricakrou.artisanat.domain.common.port.BasePort

interface IManageAnnouncement : BasePort<Announcement> {

    fun findMyAnnouncements( username : String ) : List<Announcement>

    fun findOtherAnnouncements( username : String ) : List<Announcement>

    fun findAnnouncementsBySpeciality( id : Long ) : List<Announcement>

    fun findAllByDelete( delete : Boolean ) : List<Announcement>

}