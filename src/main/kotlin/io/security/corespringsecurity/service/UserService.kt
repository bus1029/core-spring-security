package io.security.corespringsecurity.service

import io.security.corespringsecurity.domain.Account

interface UserService {
  fun createUser(account: Account)
}