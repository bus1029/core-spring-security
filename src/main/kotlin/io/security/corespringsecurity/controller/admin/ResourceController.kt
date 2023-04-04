package io.security.corespringsecurity.controller.admin

import io.security.corespringsecurity.domain.dto.ResourcesDto
import io.security.corespringsecurity.domain.entity.Resources
import io.security.corespringsecurity.domain.entity.Role
import io.security.corespringsecurity.repository.RoleRepository
import io.security.corespringsecurity.service.ResourcesService
import io.security.corespringsecurity.service.RoleService
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

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

  @GetMapping(value = ["/admin/resources/{id}"])
  @Throws(java.lang.Exception::class)
  fun getResources(@PathVariable id: String, model: Model): String {
    val roleList = roleService.getRoles()
    model.addAttribute("roleList", roleList)

    val resources = resourcesService.getResources(id.toLong())
    val modelMapper = ModelMapper()
    val resourcesDto: ResourcesDto = modelMapper.map(resources, ResourcesDto::class.java)
    model.addAttribute("resources", resourcesDto)
    return "admin/resource/detail"
  }

  @PostMapping(value = ["/admin/resources"])
  @Throws(java.lang.Exception::class)
  fun createResources(resourcesDto: ResourcesDto): String? {
    val modelMapper = ModelMapper()
    val role = roleRepository.findByRoleName(resourcesDto.roleName)
    val roles = HashSet<Role>()
    if (role != null) {
      roles.add(role)
    }

    val resources = modelMapper.map(
      resourcesDto,
      Resources::class.java
    )
    resources.roleSet = roles
    resourcesService.createResources(resources)

//    urlSecurityMetadataSource.reload()
//    methodSecurityService.addMethodSecured(resourcesDto.resourceName, resourcesDto.roleName)
    return "redirect:/admin/resources"
  }

  @GetMapping(value = ["/admin/resources/register"])
  @Throws(java.lang.Exception::class)
  fun viewRoles(model: Model): String? {
    val roleList = roleService.getRoles()
    model.addAttribute("roleList", roleList)
    val resources = ResourcesDto()
    val roleSet: MutableSet<Role> = HashSet()
    roleSet.add(Role())
    resources.roleSet = roleSet
    model.addAttribute("resources", resources)
    return "admin/resource/detail"
  }
}