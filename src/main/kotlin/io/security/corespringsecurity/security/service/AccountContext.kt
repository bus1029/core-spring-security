package io.security.corespringsecurity.security.service

import io.security.corespringsecurity.domain.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AccountContext(
  account: Account,
  authorities: MutableCollection<out GrantedAuthority>?) : User(account.username, account.password, authorities) {

    val account = account
}