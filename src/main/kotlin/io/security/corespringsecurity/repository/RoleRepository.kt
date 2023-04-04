package io.security.corespringsecurity.repository

import io.security.corespringsecurity.domain.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
  fun findByRoleName(name: String): Role?
}