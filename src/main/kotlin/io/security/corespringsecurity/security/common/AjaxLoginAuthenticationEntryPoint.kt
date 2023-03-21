package io.security.corespringsecurity.security.common

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AjaxLoginAuthenticationEntryPoint : AuthenticationEntryPoint {
  override fun commence(
    request: HttpServletRequest?,
    response: HttpServletResponse,
    authException: AuthenticationException
  ) {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
      "Unauthorized")
  }
}