package io.security.corespringsecurity.controller.admin

import io.security.corespringsecurity.domain.dto.RoleDto
import io.security.corespringsecurity.domain.entity.Role
import io.security.corespringsecurity.service.RoleService
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RoleController(
  private val roleService: RoleService
) {
  @GetMapping(value = ["/admin/roles"])
  @Throws(Exception::class)
  fun getRoles(model: Model): String {
    val roles = roleService.getRoles()
    model.addAttribute("roles", roles)
    return "admin/role/list"
  }

  @GetMapping(value = ["/admin/roles/register"])
  @Throws(java.lang.Exception::class)
  fun viewRoles(model: Model): String {
    val role = RoleDto()
    model.addAttribute("role", role)
    return "admin/role/detail"
  }

  @PostMapping(value = ["/admin/roles"])
  @Throws(java.lang.Exception::class)
  fun createRole(roleDto: RoleDto): String {
    val modelMapper = ModelMapper()
    val role = modelMapper.map(
      roleDto,
      Role::class.java
    )

    roleService.createRole(role)
    return "redirect:/admin/roles"
  }

  @GetMapping(value = ["/admin/roles/{id}"])
  @Throws(java.lang.Exception::class)
  fun getRole(@PathVariable id: String, model: Model): String {
    val role = roleService.getRole(id.toLong())
    val modelMapper = ModelMapper()
    val roleDto = modelMapper.map(role, RoleDto::class.java)
    model.addAttribute("role", roleDto)
    return "admin/role/detail"
  }
}