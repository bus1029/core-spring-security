package io.security.corespringsecurity.service.impl

import io.security.corespringsecurity.domain.Account
import io.security.corespringsecurity.repository.UserRepository
import io.security.corespringsecurity.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userService")
class UserServiceImpl(
  private val userRepository: UserRepository
) : UserService {

  @Transactional
  override fun createUser(account: Account) {
    userRepository.save(account)
  }
}