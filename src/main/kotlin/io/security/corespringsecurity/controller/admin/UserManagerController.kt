package io.security.corespringsecurity.controller.admin

import io.security.corespringsecurity.domain.entity.Account
import io.security.corespringsecurity.service.RoleService
import io.security.corespringsecurity.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserManagerController(
  private val userService: UserService,
  private val roleService: RoleService
) {
  @GetMapping(value = ["/admin/accounts"])
  @Throws(Exception::class)
  fun getUsers(model: Model): String? {
    val accounts: List<Account> = userService.getUsers()
    model.addAttribute("accounts", accounts)
    return "admin/user/list"
  }
}