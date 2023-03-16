package io.security.corespringsecurity.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.security.corespringsecurity.domain.AccountDto
import io.security.corespringsecurity.security.token.AjaxAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AjaxLoginProcessingFilter :
  AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/login")) {

  private val objectMapper = ObjectMapper().registerKotlinModule()

  override fun attemptAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?): Authentication {
    if (!isAjax(request)) {
      throw IllegalStateException("Authentication is not supported.")
    }

    val accountDto = objectMapper.readValue(request?.reader, AccountDto::class.java)
    if (!isValidAccount(accountDto)) {
      throw IllegalArgumentException("Username or Password is empty.")
    }

    return authenticationManager.authenticate(
      AjaxAuthenticationToken.unauthenticated(accountDto.username, accountDto.password))
  }

  private fun isAjax(request: HttpServletRequest?) =
    "XMLHttpRequest" == request?.getHeader("X-Requested-With")


  private fun isValidAccount(accountDto: AccountDto) =
    StringUtils.hasText(accountDto.username) &&
            StringUtils.hasText(accountDto.password)
}
