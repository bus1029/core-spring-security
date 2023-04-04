package io.security.corespringsecurity.service.impl

import io.security.corespringsecurity.domain.dto.AccountDto
import io.security.corespringsecurity.domain.entity.Account
import io.security.corespringsecurity.domain.entity.Role
import io.security.corespringsecurity.repository.RoleRepository
import io.security.corespringsecurity.repository.UserRepository
import io.security.corespringsecurity.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userService")
class UserServiceImpl(
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
  private val passwordEncoder: PasswordEncoder
) : UserService {

  @Transactional
  override fun createUser(account: Account) {
    val role = roleRepository.findByRoleName("ROLE_USER")
    val roles = HashSet<Role>()
    role?.let {
      roles.add(role)
    }
    account.userRoles = roles
    userRepository.save(account)
  }

  override fun modifyUser(accountDto: AccountDto) {
    TODO("Not yet implemented")
  }

  override fun getUsers(): List<Account> {
    return userRepository.findAll()
  }

  override fun getUser(id: Long): AccountDto {
    TODO("Not yet implemented")
  }

  override fun deleteUser(idx: Long) {
    TODO("Not yet implemented")
  }
}