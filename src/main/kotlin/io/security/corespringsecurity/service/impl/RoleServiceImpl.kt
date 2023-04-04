package io.security.corespringsecurity.service.impl

import io.security.corespringsecurity.domain.entity.Role
import io.security.corespringsecurity.repository.RoleRepository
import io.security.corespringsecurity.service.RoleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleServiceImpl(
  private val roleRepository: RoleRepository
) : RoleService {
  @Transactional
  override fun getRole(id: Long): Role {
    return roleRepository.findById(id).orElse(Role())
  }

  @Transactional
  override fun getRoles(): List<Role> {
    return roleRepository.findAll()
  }

  @Transactional
  override fun createRole(role: Role) {
    roleRepository.save(role)
  }

  @Transactional
  override fun deleteRole(id: Long) {
    roleRepository.deleteById(id)
  }
}