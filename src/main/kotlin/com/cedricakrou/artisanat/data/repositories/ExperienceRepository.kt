package com.cedricakrou.artisanat.data.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface ExperienceRepository : JpaRepository<Experience, Long> {
}