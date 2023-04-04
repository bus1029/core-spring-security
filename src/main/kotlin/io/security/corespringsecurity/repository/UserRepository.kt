package io.security.corespringsecurity.repository

import io.security.corespringsecurity.domain.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Account, Long> {
  fun findByUsername(username: String?): Account?
}