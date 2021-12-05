package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.experience.entity.Experience
import org.springframework.data.jpa.repository.JpaRepository

interface ExperienceRepository : JpaRepository<Experience, Long> {
}