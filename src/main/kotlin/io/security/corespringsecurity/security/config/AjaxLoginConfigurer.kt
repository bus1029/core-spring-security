package io.security.corespringsecurity.security.config

import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.HttpSecurityBuilder
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.stereotype.Component

class AjaxLoginConfigurer<H : HttpSecurityBuilder<H>> :
  AbstractAuthenticationFilterConfigurer<H, AjaxLoginConfigurer<H>, AjaxLoginProcessingFilter> {

  private var successHandler: AuthenticationSuccessHandler? = null
  private var failureHandler: AuthenticationFailureHandler? = null
  private var authenticationManager: AuthenticationManager? = null

  constructor() : super(AjaxLoginProcessingFilter(), "/api/login") {

  }

  override fun init(http: H) {
    super.init(http)
  }

  override fun configure(http: H) {
    if (authenticationManager == null) {
      authenticationManager = http.getSharedObject(AuthenticationManager::class.java)
    }

    authenticationFilter.setAuthenticationManager(authenticationManager)
    authenticationFilter.setAuthenticationSuccessHandler(successHandler)
    authenticationFilter.setAuthenticationFailureHandler(failureHandler)

    val sessionAuthenticationStrategy =
      http.getSharedObject(SessionAuthenticationStrategy::class.java)
    sessionAuthenticationStrategy?.let {
      authenticationFilter.setSessionAuthenticationStrategy(it)
    }

    val rememberMeServices =
      http.getSharedObject(RememberMeServices::class.java)
    rememberMeServices?.let {
      authenticationFilter.rememberMeServices = it
    }

    http.setSharedObject(AjaxLoginProcessingFilter::class.java, authenticationFilter)
    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
  }

  fun successHandlerAjax(successHandler: AuthenticationSuccessHandler): AjaxLoginConfigurer<H> {
    this.successHandler = successHandler
    return this
  }

  fun failureHandlerAjax(failureHandler: AuthenticationFailureHandler): AjaxLoginConfigurer<H> {
    this.failureHandler = failureHandler
    return this
  }

  fun setAuthenticationManager(authenticationManager: AuthenticationManager): AjaxLoginConfigurer<H> {
    this.authenticationManager = authenticationManager
    return this
  }

  override fun createLoginProcessingUrlMatcher(loginProcessingUrl: String?): RequestMatcher {
    return AntPathRequestMatcher(loginProcessingUrl, "POST")
  }
}
