package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.formation.entity.Formation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormationRepository : JpaRepository< Formation, Long > {
}