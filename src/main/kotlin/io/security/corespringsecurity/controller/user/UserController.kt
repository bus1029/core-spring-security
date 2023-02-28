package io.security.corespringsecurity.controller.user

import io.security.corespringsecurity.domain.Account
import io.security.corespringsecurity.domain.AccountDto
import io.security.corespringsecurity.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class UserController(
  private val passwordEncoder: PasswordEncoder,
  private val userService: UserService
) {
  @GetMapping("/mypage")
  fun myPage(): String {
    return "user/mypage"
  }

  @GetMapping("/users")
  fun createUser(): String {
    return "user/login/register"
  }

  @PostMapping("/users")
  fun createUser(accountDto: AccountDto): String {
    val account = mapToAccount(accountDto)
    account.password = passwordEncoder.encode(account.password)
    userService.createUser(account)

    return "redirect:/"
  }

  private fun mapToAccount(accountDto: AccountDto): Account {
    val modelMapper = ModelMapper()
    return modelMapper.map(accountDto, Account::class.java)
  }
}