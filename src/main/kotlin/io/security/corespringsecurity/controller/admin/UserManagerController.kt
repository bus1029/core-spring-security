package io.security.corespringsecurity.controller.admin

import io.security.corespringsecurity.domain.dto.AccountDto
import io.security.corespringsecurity.domain.entity.Account
import io.security.corespringsecurity.service.RoleService
import io.security.corespringsecurity.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

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

  @GetMapping(value = ["/admin/accounts/{id}"])
  fun getUser(@PathVariable(value = "id") id: Long, model: Model): String {
    val accountDto = userService.getUser(id)
    val roleList = roleService.getRoles()
    model.addAttribute("account", accountDto)
    model.addAttribute("roleList", roleList)
    return "admin/user/detail"
  }

  @PostMapping(value = ["/admin/accounts"])
  @Throws(java.lang.Exception::class)
  fun modifyUser(accountDto: AccountDto): String {
    userService.modifyUser(accountDto)
    return "redirect:/admin/accounts"
  }
}