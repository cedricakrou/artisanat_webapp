package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.account.entity.client.Client
import com.cedricakrou.artisanat.domain.announcement.entity.Announcement
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AnnouncementRepository : JpaRepository<Announcement, Long>  {

    fun findAllByClient_Username( username : String ) : List<Announcement>
    fun findAllBySpeciality_Id( id : Long ) : List<Announcement>
    fun findAllBySpeciality_IdAndDelete( id : Long, delete: Boolean ) : List<Announcement>

    fun findAllByClient_UsernameNot( username : String ) : List<Announcement>

    fun findAllByDelete( delete : Boolean ) : List<Announcement>

}