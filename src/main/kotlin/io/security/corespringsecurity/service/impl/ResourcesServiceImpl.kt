package io.security.corespringsecurity.service.impl

import io.security.corespringsecurity.domain.entity.Resources
import io.security.corespringsecurity.repository.ResourcesRepository
import io.security.corespringsecurity.service.ResourcesService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ResourcesServiceImpl(
  private val resourcesRepository: ResourcesRepository
) : ResourcesService {
  override fun getResources(id: Long): Resources {
    return resourcesRepository.findById(id).orElse(Resources())
  }

  override fun getResources(): List<Resources> {

    return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")))
  }

  override fun createResources(resources: Resources) {
    resourcesRepository.save(resources)
  }

  override fun deleteResources(id: Long) {
    resourcesRepository.deleteById(id)
  }
}