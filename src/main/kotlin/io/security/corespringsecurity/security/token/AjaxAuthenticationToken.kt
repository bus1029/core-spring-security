package io.security.corespringsecurity.security.token

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.SpringSecurityCoreVersion
import org.springframework.util.Assert

class AjaxAuthenticationToken : AbstractAuthenticationToken {
  private val serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID
  private var principal: Any?
  private var credentials: Any?

  constructor(principal: Any?,
              credentials: Any?): super(null) {
    this.principal = principal
    this.credentials = credentials
    isAuthenticated = false
  }

  constructor (
    principal: Any?,
    credentials: Any?,
    authorities: Collection<GrantedAuthority>?) : super(authorities) {
    this.principal = principal
    this.credentials = credentials
    super.setAuthenticated(true)
  }

  companion object {
    fun unauthenticated(principal: Any?,
                        credentials: Any?): AjaxAuthenticationToken {
      return AjaxAuthenticationToken(principal, credentials)
    }

    fun authenticated(principal: Any?,
                        credentials: Any?,
                        authorities: Collection<GrantedAuthority>?): AjaxAuthenticationToken {
      return AjaxAuthenticationToken(principal, credentials, authorities)
    }
  }

  override fun getCredentials(): Any? {
    return this.credentials
  }

  override fun getPrincipal(): Any? {
    return this.principal
  }

  override fun setAuthenticated(authenticated: Boolean) {
    Assert.isTrue(!isAuthenticated,
      "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead")
    super.setAuthenticated(false)
  }

  override fun eraseCredentials() {
    super.eraseCredentials()
    this.credentials = null
  }
}