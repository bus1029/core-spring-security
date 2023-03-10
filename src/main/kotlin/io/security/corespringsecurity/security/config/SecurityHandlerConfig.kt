package io.security.corespringsecurity.security.config

import io.security.corespringsecurity.security.handler.CustomAccessDeniedHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.access.AccessDeniedHandler

@Configuration
class SecurityHandlerConfig {
  @Bean
  fun accessDeniedHandler(): AccessDeniedHandler {
    return CustomAccessDeniedHandler("/denied")
  }
}