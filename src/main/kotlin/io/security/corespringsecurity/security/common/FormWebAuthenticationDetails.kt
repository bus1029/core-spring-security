package io.security.corespringsecurity.security.common

import org.springframework.security.web.authentication.WebAuthenticationDetails
import javax.servlet.http.HttpServletRequest

class FormWebAuthenticationDetails : WebAuthenticationDetails {
  var secretKey = ""

  constructor(request: HttpServletRequest?) : super(request){
    secretKey = request?.getParameter("secret_key") ?: ""
  }
}