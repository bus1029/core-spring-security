package io.security.corespringsecurity.domain.entity

import java.util.LinkedHashSet
import javax.persistence.*

@Entity
class Role(
  var roleName: String = "",
  var roleDesc: String = ""
) {
  @Id
  @GeneratedValue
  var id: Long? = null

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
  @OrderBy("ordernum desc")
  var resourcesSet: Set<Resources> = LinkedHashSet()
}