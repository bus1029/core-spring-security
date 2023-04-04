package io.security.corespringsecurity.service

import io.security.corespringsecurity.domain.entity.Role

interface RoleService {
  fun getRole(id: Long): Role
  fun getRoles(): List<Role>
  fun createRole(role: Role)
  fun deleteRole(id: Long)
}