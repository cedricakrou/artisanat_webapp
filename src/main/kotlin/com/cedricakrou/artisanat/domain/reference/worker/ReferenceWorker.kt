package com.cedricakrou.artisanat.domain.reference.worker

import com.cedricakrou.artisanat.data.repositories.ReferenceRepository
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.reference.entity.Reference
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReferenceWorker( val repository: ReferenceRepository ) : ReferenceDomain
{

    override fun save(model: Reference): OperationResult<Reference> {

        repository.save( model )

        return OperationResult( HashMap<String, String>(), model )

    }

    override fun findById(id: Long): Optional<Reference> {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Reference> {
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