package com.cedricakrou.artisanat.domain.formation.worker

import com.cedricakrou.artisanat.data.repositories.FormationRepository
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.formation.entity.Formation
import com.cedricakrou.artisanat.domain.formation.port.IManageFormation
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class FormationWorker( val repository: FormationRepository ) : FormationDomain {

    override fun save(model: Formation): OperationResult<Formation> {

        repository.save( model )

        return OperationResult( HashMap<String, String>(), model )
    }

    override fun findById(id: Long): Optional<Formation> {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Formation> {
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