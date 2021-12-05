package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.account.entity.artisan.Artisan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtisanRepository : JpaRepository<Artisan, Long> {

    fun findAllByVisible( visible : Boolean ) : List<Artisan>

}