package com.cedricakrou.artisanat.domain.account.entity.artisan

import com.cedricakrou.artisanat.data.repositories.ArtisanRepository
import org.springframework.stereotype.Service

@Service
class IManageArtisan( val artisanRepository: ArtisanRepository ) {

    fun  findAllByVisible( visible : Boolean ) : List<Artisan>
        = artisanRepository.findAllByVisible( visible )

}