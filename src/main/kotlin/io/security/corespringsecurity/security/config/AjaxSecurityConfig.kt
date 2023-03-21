package io.security.corespringsecurity.security.config

import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter
import io.security.corespringsecurity.security.handler.AjaxAuthenticationFailureHandler
import io.security.corespringsecurity.security.handler.AjaxAuthenticationSuccessHandler
import io.security.corespringsecurity.security.provider.AjaxAuthenticationProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
@Order(0)
class AjaxSecurityConfig(
  private val userDetailsService: UserDetailsService,
  private val passwordEncoder: PasswordEncoder
) {
  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    setAuthorizeAndAuthentication(http)
    setAjaxLoginFilter(http)
    return http.build()
  }

  private fun setAuthorizeAndAuthentication(http: HttpSecurity) {
    http
      .antMatcher("/api/**")
      .authorizeRequests()
      .anyRequest().authenticated()
  }

  private fun setAjaxLoginFilter(http: HttpSecurity) {
    http
      .addFilterBefore(ajaxLoginProcessingFilter(http), UsernamePasswordAuthenticationFilter::class.java)
      .csrf().disable()
  }

  @Bean
  fun ajaxLoginProcessingFilter(http: HttpSecurity): AjaxLoginProcessingFilter {
    val authenticationManager = authenticationManager(http)
    return AjaxLoginProcessingFilter().apply {
      this.setAuthenticationManager(authenticationManager)
      this.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler())
      this.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler())
    }
  }

  private fun authenticationManager(http: HttpSecurity): AuthenticationManager? {
    val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
    return builder
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder)
      .and()
      .authenticationProvider(ajaxAuthenticationProvider())
      .build()
  }

  @Bean
  fun ajaxAuthenticationProvider(): AuthenticationProvider {
    return AjaxAuthenticationProvider(userDetailsService, passwordEncoder)
  }

  @Bean
  fun ajaxAuthenticationSuccessHandler(): AjaxAuthenticationSuccessHandler {
    return AjaxAuthenticationSuccessHandler()
  }

  @Bean
  fun ajaxAuthenticationFailureHandler(): AjaxAuthenticationFailureHandler {
    return AjaxAuthenticationFailureHandler()
  }
}