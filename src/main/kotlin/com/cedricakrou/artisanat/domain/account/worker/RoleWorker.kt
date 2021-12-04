package com.cedricakrou.artisanat.domain.account.worker

import com.cedricakrou.artisanat.domain.account.entity.Role
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleWorker( private val roleRepository : RoleRepository) : RoleDomain {

    override fun save(role: Role): Role = roleRepository.save(role)

    override fun count(): Long = roleRepository.count()

    override fun findByName(name: String): Optional<Role> = roleRepository.findByName(name)

    override fun findById(id: Long): Optional<Role> = roleRepository.findById(id)

    override fun findAll(): List<Role> = roleRepository.findAll()
}