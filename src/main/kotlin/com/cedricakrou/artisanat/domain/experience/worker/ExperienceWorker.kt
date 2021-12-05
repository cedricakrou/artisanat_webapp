package com.cedricakrou.artisanat.domain.experience.worker

import com.cedricakrou.artisanat.data.repositories.ExperienceRepository
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.experience.entity.Experience
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExperienceWorker( val repository : ExperienceRepository ) : ExperienceDomain {

    override fun save(model: Experience): OperationResult<Experience> {

        repository.save( model )

        return OperationResult( HashMap<String, String>(), model )
        
    }

    override fun findById(id: Long): Optional<Experience> {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Experience> {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }
}