package io.security.corespringsecurity.security.config

import io.security.corespringsecurity.security.common.AjaxLoginAuthenticationEntryPoint
import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter
import io.security.corespringsecurity.security.handler.AjaxAccessDeniedHandler
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
    setExceptionHandling(http)
    return http.build()
  }

  private fun setAuthorizeAndAuthentication(http: HttpSecurity) {
    http
      .antMatcher("/api/**")
      .authorizeRequests()
      .antMatchers("/api/messages").hasRole("MANAGER")
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
      .authenticationProvider(authenticationProvider())
      .build()
  }

  @Bean
  fun authenticationProvider() = ajaxAuthenticationProvider()

  private fun setExceptionHandling(http: HttpSecurity) {
    http.exceptionHandling()
      .authenticationEntryPoint(AjaxLoginAuthenticationEntryPoint())
      .accessDeniedHandler(AjaxAccessDeniedHandler())
  }

  @Bean
  fun ajaxAuthenticationProvider() =
    AjaxAuthenticationProvider(userDetailsService, passwordEncoder)

  @Bean("ajaxSuccessHandler")
  fun ajaxAuthenticationSuccessHandler() = AjaxAuthenticationSuccessHandler()

  @Bean("ajaxFailureHandler")
  fun ajaxAuthenticationFailureHandler() = AjaxAuthenticationFailureHandler()
}