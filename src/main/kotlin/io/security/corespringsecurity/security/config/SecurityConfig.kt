package io.security.corespringsecurity.security.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
class SecurityConfig {
  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http.authorizeRequests()
      .antMatchers("/", "/users").permitAll()
      .antMatchers("/mypage").hasRole("USER")
      .antMatchers("/messages").hasRole("MANAGER")
      .antMatchers("/config").hasRole("ADMIN")
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/login_proc")
      .defaultSuccessUrl("/")
      .permitAll()

    return http.build()
  }

//  @Bean
//  fun webSecurityCustomizer(): WebSecurityCustomizer {
//    return WebSecurityCustomizer { web: WebSecurity ->
//      web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//    }
//  }
}