package io.security.corespringsecurity.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AjaxAuthenticationFailureHandler : AuthenticationFailureHandler {
  private val objectMapper = ObjectMapper()

  override fun onAuthenticationFailure(
    request: HttpServletRequest,
    response: HttpServletResponse,
    exception: AuthenticationException
  ) {
    var errMsg = "Invalid username or password"
    when (exception) {
      is BadCredentialsException -> {
        errMsg = "Invalid username or password."
      }
      is DisabledException -> {
        errMsg = "Locked"
      }
      is CredentialsExpiredException -> {
        errMsg = "Expired password"
      }
    }

    response.status = HttpStatus.UNAUTHORIZED.value()
    response.contentType = MediaType.APPLICATION_JSON_VALUE
    objectMapper.writeValue(response.writer, errMsg)
  }
}