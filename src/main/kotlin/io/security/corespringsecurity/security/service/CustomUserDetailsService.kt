package io.security.corespringsecurity.security.service

import io.security.corespringsecurity.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userDetailsService")
class CustomUserDetailsService(
  private val userRepository: UserRepository
) : UserDetailsService {
  @Throws(UsernameNotFoundException::class)
  @Transactional
  override fun loadUserByUsername(username: String?): UserDetails {
    val account = userRepository.findByUsername(username)
      ?: throw UsernameNotFoundException("UsernameNotFoundException")

    val roles = mutableListOf<SimpleGrantedAuthority>()
    for (userRole in account.userRoles!!) {
      roles.add(SimpleGrantedAuthority(userRole.roleName))
    }

    return AccountContext(account, roles)
  }
}