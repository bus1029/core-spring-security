package io.security.corespringsecurity.controller.admin

import io.security.corespringsecurity.service.RoleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

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
}