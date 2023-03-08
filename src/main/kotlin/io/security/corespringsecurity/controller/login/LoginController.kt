package io.security.corespringsecurity.controller.login

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
  @GetMapping("/login")
  fun login(): String {
    return "user/login/login"
  }
}