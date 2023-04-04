package io.security.corespringsecurity.domain.entity

import java.util.HashSet
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
class Resources(
  var resourceName: String = "",
  var httpMethod: String = "",
  var orderNum: Int? = null,
  var resourceType: String = ""
) {
  @Id
  @GeneratedValue
  @Column(name = "resource_id")
  var id: Long? = null

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_resources",
    joinColumns = [JoinColumn(name = "resource_id")],
    inverseJoinColumns = [JoinColumn(name = "role_id")])
  var roleSet: Set<Role>? = HashSet()
}