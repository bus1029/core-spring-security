package io.security.corespringsecurity.controller.admin

import io.security.corespringsecurity.domain.entity.Resources
import io.security.corespringsecurity.repository.RoleRepository
import io.security.corespringsecurity.service.ResourcesService
import io.security.corespringsecurity.service.RoleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ResourceController(
  private val resourcesService: ResourcesService,
  private val roleRepository: RoleRepository,
  private val roleService: RoleService
) {
  @GetMapping(value = ["/admin/resources"])
  @Throws(Exception::class)
  fun getResources(model: Model): String? {
    val resources: List<Resources> = resourcesService.getResources()
    model.addAttribute("resources", resources)
    return "admin/resource/list"
  }
}