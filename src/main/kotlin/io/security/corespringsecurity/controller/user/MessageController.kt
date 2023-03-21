package io.security.corespringsecurity.controller.user

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MessageController {
  @GetMapping("/messages")
  fun messages(): String {
    return "user/messages"
  }

  @GetMapping("/api/messages")
  @ResponseBody
  fun apiMessages(): String {
    return "messages ok"
  }
}