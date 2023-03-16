package io.security.corespringsecurity.security.config

import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetails
import javax.servlet.http.HttpServletRequest

@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
class SecurityConfig(
  private val authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>,
  private val customSuccessHandler: AuthenticationSuccessHandler,
  private val customFailureHandler: AuthenticationFailureHandler,
  private val customAccessDeniedHandler: AccessDeniedHandler,
  private val userDetailsService: UserDetailsService,
  private val passwordEncoder: PasswordEncoder
) {
  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    setAuthorizationAndAuthentication(http)
    setFormLogin(http)
    setExceptionHandling(http)
    setAjaxLoginFilter(http)
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

  private fun setAjaxLoginFilter(http: HttpSecurity) {
    http.addFilterBefore(ajaxLoginProcessingFilter(http), UsernamePasswordAuthenticationFilter::class.java)
      .csrf().disable()
  }

  @Bean
  fun ajaxLoginProcessingFilter(http: HttpSecurity): AjaxLoginProcessingFilter {
    return AjaxLoginProcessingFilter().apply {
      val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
      val authenticationManager = builder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder)
        .and()
        .build()

      this.setAuthenticationManager(authenticationManager)
    }
  }

  @Bean
  fun webSecurityCustomizer(): WebSecurityCustomizer {
    return WebSecurityCustomizer { web: WebSecurity ->
      web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }
  }
}