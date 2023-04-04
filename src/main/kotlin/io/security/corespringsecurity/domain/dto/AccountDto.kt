package io.security.corespringsecurity.domain.dto

class AccountDto(
  var id: String? = null,
  var username: String = "",
  var password: String = "",
  var email: String = "",
  var age: Int = 0,
  var roles: List<String>? = null
)
