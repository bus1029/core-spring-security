package io.security.corespringsecurity.security.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.WebAuthenticationDetails
import javax.servlet.http.HttpServletRequest

@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
@Order(1)
class SecurityConfig(
  private val authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>,
  private val customSuccessHandler: AuthenticationSuccessHandler,
  private val customFailureHandler: AuthenticationFailureHandler,
  private val customAccessDeniedHandler: AccessDeniedHandler
) {
  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    setAuthorizationAndAuthentication(http)
    setFormLogin(http)
    setExceptionHandling(http)
    return http.build()
  }

  fun setAuthorizationAndAuthentication(http: HttpSecurity) {
    http.authorizeRequests()
      .antMatchers("/", "/users", "user/login/**", "/login*").permitAll()
      .antMatchers("/mypage").hasRole("USER")
      .antMatchers("/messages").hasRole("MANAGER")
      .antMatchers("/config").hasRole("ADMIN")
      .anyRequest().authenticated()
  }

  fun setFormLogin(http: HttpSecurity) {
    http.formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/login_proc")
      .authenticationDetailsSource(authenticationDetailsSource)
      .defaultSuccessUrl("/")
      .successHandler(customSuccessHandler)
      .failureHandler(customFailureHandler)
      .permitAll()
  }

  private fun setExceptionHandling(http: HttpSecurity) {
    http.exceptionHandling()
      .accessDeniedHandler(customAccessDeniedHandler)
  }

  @Bean
  fun webSecurityCustomizer(): WebSecurityCustomizer {
    return WebSecurityCustomizer { web: WebSecurity ->
      web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }
  }
}