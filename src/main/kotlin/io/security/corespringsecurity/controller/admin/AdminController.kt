package io.security.corespringsecurity.controller.admin

import org.aspectj.bridge.Message
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class AdminController {
  @GetMapping(value = ["/admin"])
  @Throws(Exception::class)
  fun home(model: Model): String? {
    val authentication = SecurityContextHolder.getContext().authentication
    val principal = authentication.principal
    var username = ""
    if (principal != null && principal is User) {
      username = principal.username
    }

    model["userId"] = username
    return "admin/home"
  }

//  @PreAuthorize("isAuthenticated() and (( #user.name == principal.name ) or hasRole('ROLE_ADMIN'))")
//  @RequestMapping(value = [""], method = [RequestMethod.PUT])
//  fun updateUser(user: User?): ResponseEntity<Message?>? {
//    messageService.updateMessage(user)
//    return ResponseEntity(Message("", null, true), HttpStatus.OK)
//  }
}