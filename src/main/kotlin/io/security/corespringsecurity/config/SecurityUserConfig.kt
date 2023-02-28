package io.security.corespringsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class SecurityUserConfig {
    @Bean
    fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager(
                createUser(),
                createSys(),
                createAdmin()
        )
    }

    private fun createUser(): UserDetails {
      return User.builder()
                .username("user")
                .password(passwordEncoder().encode("user1111"))
                .roles("USER")
                .build()
    }

    private fun createSys(): UserDetails {
      return User.builder()
                .username("manager")
                .password(passwordEncoder().encode("manager1111"))
                .roles("USER", "MANAGER")
                .build()
    }

    private fun createAdmin(): UserDetails {
        return User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin1111"))
                .roles("USER", "MANAGER", "ADMIN")
                .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
      return BCryptPasswordEncoder()
    }
}