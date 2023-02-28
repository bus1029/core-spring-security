package io.security.corespringsecurity.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Account {

  @Id
  @GeneratedValue
  var id: Long? = null
  var username: String = ""
  var password: String = ""
  var email: String = ""
  var age: String = ""
  var role: String = ""
}