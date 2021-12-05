package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.account.entity.client.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {

    fun findAllByDelete( delete : Boolean ) : List<Client>

}