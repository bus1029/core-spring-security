package io.security.corespringsecurity.service

import io.security.corespringsecurity.domain.dto.AccountDto
import io.security.corespringsecurity.domain.entity.Account

interface UserService {
  fun createUser(account: Account)
  fun modifyUser(accountDto: AccountDto)
  fun getUsers(): List<Account>
  fun getUser(id: Long): AccountDto
  fun deleteUser(id: Long)
}