package io.security.corespringsecurity.security.handler

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component("customFailureHandler")
class CustomAuthenticationFailureHandler() : SimpleUrlAuthenticationFailureHandler() {
  override fun onAuthenticationFailure(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    exception: AuthenticationException?
  ) {
    var errorMessage = "Invalid username or password."
    if (exception is BadCredentialsException) {
      errorMessage = "Invalid username or password."
    } else if (exception is InsufficientAuthenticationException) {
      errorMessage = "Invalid secret key."
    }

    setDefaultFailureUrl("/login?error=true&exception=$errorMessage")
    super.onAuthenticationFailure(request, response, exception)
  }
}