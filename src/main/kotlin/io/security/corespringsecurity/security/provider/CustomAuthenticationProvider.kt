package io.security.corespringsecurity.security.provider

import io.security.corespringsecurity.security.common.FormWebAuthenticationDetails
import io.security.corespringsecurity.security.service.AccountContext
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
  private val userDetailsService: UserDetailsService,
  private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

  override fun authenticate(authentication: Authentication): Authentication {
    val username = authentication.name
    val password = authentication.credentials as String

    val accountContext = userDetailsService.loadUserByUsername(username) as AccountContext
    if (!passwordEncoder.matches(password, accountContext.password)) {
      throw BadCredentialsException("Invalid password")
    }

    val details = authentication.details as FormWebAuthenticationDetails
    if ("secret" != details.secretKey) {
      throw InsufficientAuthenticationException("InsufficientAuthenticationException")
    }

    return UsernamePasswordAuthenticationToken(accountContext.account, null, accountContext.authorities)
  }

  override fun supports(authentication: Class<*>?): Boolean {
    return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
  }
}