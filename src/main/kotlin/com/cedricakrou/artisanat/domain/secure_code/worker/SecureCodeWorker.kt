package com.cedricakrou.artisanat.domain.secure_code.worker

import com.cedricakrou.artisanat.data.repositories.SecureCodeRepository
import com.cedricakrou.artisanat.domain.common.OperationResult
import com.cedricakrou.artisanat.domain.secure_code.entity.SecureCode
import org.springframework.stereotype.Service
import java.util.*

@Service
class SecureCodeWorker( val repository: SecureCodeRepository) : SecureCodeDomain {

    override fun findByMemberNoAndCode(memberNo: String, code: String): Optional<SecureCode> =
        repository.findByMemberNoAndCode(memberNo, code)

    override fun save(model: SecureCode): OperationResult<SecureCode> {
        val errors : HashMap<String, String> = HashMap()

        if ( model.memberNo.isEmpty() ){
            errors["memberNoEmpty"]  = "Veuillez renseigner le numéro de l'assuré"
        }

        if ( model.code.isEmpty() ){
            errors["codeEmpty"]  = "Veuillez renseigner le code de securité"
        }

        if ( errors.isEmpty() ){
            repository.save(model)
        }

        return OperationResult( errors, model )
    }

    override fun findById(id: Long): Optional<SecureCode> = repository.findById(id)

    override fun findAll(): List<SecureCode> = repository.findAll()

    override fun deleteAll() = repository.deleteAll()

    override fun deleteById(id: Long) = repository.deleteById(id)

    override fun count(): Long = repository.count()
}