package io.security.corespringsecurity.security.config

import io.security.corespringsecurity.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityUserConfig(
  private val userRepository: UserRepository
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}