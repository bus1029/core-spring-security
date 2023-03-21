package io.security.corespringsecurity.security.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component("customSuccessHandler")
class CustomAuthenticationSuccessHandler :
  SimpleUrlAuthenticationSuccessHandler() {

  private val requestCache = HttpSessionRequestCache()
  private val redirectStrategy = DefaultRedirectStrategy()

  override fun onAuthenticationSuccess(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    authentication: Authentication?
  ) {
    defaultTargetUrl = "/"
    val savedRequest = requestCache.getRequest(request, response)
    // 이전 정보가 없는 경우엔 생성되지 않음
    savedRequest?.let {
      redirectStrategy.sendRedirect(request, response, it.redirectUrl)
    } ?: redirectStrategy.sendRedirect(request, response, defaultTargetUrl)

  }
}