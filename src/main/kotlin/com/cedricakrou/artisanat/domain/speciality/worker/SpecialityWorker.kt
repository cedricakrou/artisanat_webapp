package com.cedricakrou.artisanat.domain.speciality.worker

import com.cedricakrou.artisanat.data.repositories.SpecialityRepository
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.speciality.entity.Speciality
import org.springframework.stereotype.Service
import java.util.*

@Service
class SpecialityWorker( val repository: SpecialityRepository) : SpecialityDomain {

    override fun findByName(name: String): Optional<Speciality> = repository.findByName( name )

    override fun save(model: Speciality): OperationResult<Speciality> {

        val errors : HashMap<String, String> = HashMap()

        if ( model.name.isEmpty()) {
            errors["nameIsEmpty"] = "Veuillez renseigner le nom de la specialité"
        }
        else if ( repository.countByName( model.name ) != 0L ) {
            errors["nameExists"] = "Le nom de la specialité existe dejà"
        }

        if ( errors.isEmpty() ) {
            repository.save(model)
        }

        return OperationResult(errors, model)


    }

    override fun findById(id: Long): Optional<Speciality> = repository.findById(id)

    override fun findAll(): List<Speciality> = repository.findAll()

    override fun deleteAll() = repository.deleteAll()

    override fun deleteById(id: Long) = repository.deleteById(id)

    override fun count(): Long = repository.count()
}