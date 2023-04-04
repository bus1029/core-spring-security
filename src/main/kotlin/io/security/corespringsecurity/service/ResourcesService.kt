package io.security.corespringsecurity.service

import io.security.corespringsecurity.domain.entity.Resources

interface ResourcesService {
  fun getResources(id: Long): Resources
  fun getResources(): List<Resources>
  fun createResources(resources: Resources)
  fun deleteResources(id: Long)
}