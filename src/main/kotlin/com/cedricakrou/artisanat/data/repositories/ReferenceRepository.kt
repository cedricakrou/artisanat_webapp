package com.cedricakrou.artisanat.data.repositories

import com.cedricakrou.artisanat.domain.reference.entity.Reference
import org.springframework.data.jpa.repository.JpaRepository

interface ReferenceRepository : JpaRepository<Reference, Long>