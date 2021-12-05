package com.cedricakrou.artisanat.domain.account.entity.client

import com.cedricakrou.artisanat.data.repositories.ClientRepository
import org.springframework.stereotype.Service

@Service
class IManageClient( private val repository : ClientRepository ) {

    fun findAllByDelete( delete : Boolean ) : List<Client>
        = repository.findAllByDelete(false)

}