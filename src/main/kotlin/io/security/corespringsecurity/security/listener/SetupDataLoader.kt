package io.security.corespringsecurity.security.listener

import io.security.corespringsecurity.domain.entity.Account
import io.security.corespringsecurity.domain.entity.Resources
import io.security.corespringsecurity.domain.entity.Role
import io.security.corespringsecurity.repository.ResourcesRepository
import io.security.corespringsecurity.repository.RoleRepository
import io.security.corespringsecurity.repository.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicInteger

@Component
class SetupDataLoader(
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
  private val resourceRepository: ResourcesRepository,
  private val passwordEncoder: PasswordEncoder
) : ApplicationListener<ContextRefreshedEvent> {
  private var alreadySetup = false

  companion object {
    private val count = AtomicInteger(0)
  }

  @Transactional
  override fun onApplicationEvent(event: ContextRefreshedEvent) {
    if (alreadySetup) {
      return
    }

    setupSecurityResources()
    alreadySetup = true
  }

  private fun setupSecurityResources() {
    val roles = HashSet<Role>()
    val adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자")
    roles.add(adminRole)
    createResourceIfNotFound("/admin/**", "", roles, "url")
    val account = createUserIfNotFound("admin", "pass", "admin@gmail.com", 10, roles)
  }

  @Transactional
  fun createRoleIfNotFound(roleName: String, roleDesc: String): Role {
    var role = roleRepository.findByRoleName(roleName)
    if (role == null) {
      role = Role(roleName, roleDesc)
    }
    return roleRepository.save(role)
  }

  @Transactional
  fun createResourceIfNotFound(
    resourceName: String,
    httpMethod: String,
    roles: Set<Role>,
    resourceType: String
  ): Resources {
    var resources = resourceRepository.findByResourceName(resourceName)
    if (resources == null) {
      resources = Resources(resourceName, httpMethod, count.incrementAndGet(), resourceType)
      resources.roleSet = roles as HashSet<Role>
    }
    return resourceRepository.save(resources)
  }

  @Transactional
  fun createUserIfNotFound(userName: String, password: String, email: String, age: Int, roles: Set<Role>): Account {
    var account = userRepository.findByUsername(userName)
    if (account == null) {
      account = Account(userName, passwordEncoder.encode(password), email, age)
      account.userRoles = roles as HashSet<Role>
    }
    return userRepository.save(account)
  }
}