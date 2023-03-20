package io.security.corespringsecurity.security.provider

import io.security.corespringsecurity.security.service.AccountContext
import io.security.corespringsecurity.security.token.AjaxAuthenticationToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

class AjaxAuthenticationProvider(
  private val userDetailsService: UserDetailsService,
  private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

  override fun authenticate(authentication: Authentication): Authentication {
    val accountContext = userDetailsService
      .loadUserByUsername(authentication.name) as AccountContext
    if (!passwordEncoder.matches(authentication.credentials as String, accountContext.password)) {
      throw BadCredentialsException("Invalid password")
    }

    return AjaxAuthenticationToken
      .authenticated(accountContext.account, null, accountContext.authorities)
  }

  override fun supports(authentication: Class<*>?): Boolean {
    return AjaxAuthenticationToken::class.java.isAssignableFrom(authentication)
  }
}