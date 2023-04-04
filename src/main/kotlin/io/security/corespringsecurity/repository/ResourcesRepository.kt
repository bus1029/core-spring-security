package io.security.corespringsecurity.repository

import io.security.corespringsecurity.domain.entity.Resources
import org.springframework.data.jpa.repository.JpaRepository

interface ResourcesRepository : JpaRepository<Resources, Long> {
  fun findByResourceName(resourceName: String): Resources?
}