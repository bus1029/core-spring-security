package io.security.corespringsecurity.service.impl

import io.security.corespringsecurity.domain.dto.AccountDto
import io.security.corespringsecurity.domain.entity.Account
import io.security.corespringsecurity.domain.entity.Role
import io.security.corespringsecurity.repository.RoleRepository
import io.security.corespringsecurity.repository.UserRepository
import io.security.corespringsecurity.service.UserService
import org.modelmapper.ModelMapper
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
    val modelMapper = ModelMapper()
    val account = modelMapper.map(accountDto, Account::class.java)

    accountDto.roles?.let {
      val roles = HashSet<Role>()
      it.forEach { role ->
        val r = roleRepository.findByRoleName(role)
        if (r != null) {
          roles.add(r)
        }
      }
      account.userRoles = roles
    }

    account.password = passwordEncoder.encode(accountDto.password)
    userRepository.save(account)
  }

  override fun getUsers(): List<Account> {
    return userRepository.findAll()
  }

  override fun getUser(id: Long): AccountDto {
    val account = userRepository.findById(id).orElse(Account())
    val modelMapper = ModelMapper()
    val accountDto = modelMapper.map(account, AccountDto::class.java)

    val roles = account.userRoles
      .map { role -> role.roleName }
      .toList()

    accountDto.roles = roles
    return accountDto
  }

  override fun deleteUser(id: Long) {
    userRepository.deleteById(id)
  }
}