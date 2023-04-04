package io.security.corespringsecurity.domain.entity

import java.util.HashSet
import javax.persistence.*

@Entity
class Account(
  var username: String = "",
  var password: String = "",
  var email: String = "",
  var age: Int = 0,
) {

  @Id
  @GeneratedValue
  var id: Long? = null

  @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  @JoinTable(name = "account_roles",
    joinColumns = [JoinColumn(name = "account_id")],
    inverseJoinColumns = [JoinColumn(name = "role_id")])
  var userRoles: Set<Role> = HashSet()
}