package io.security.corespringsecurity.domain.dto

import io.security.corespringsecurity.domain.entity.Role

class ResourcesDto(
    var id: String? = null,
    var resourceName: String = "",
    var httpMethod: String = "",
    var orderNum: Int? = null,
    var resourceType: String = "",
    var roleName: String = "",
    var roleSet: Set<Role>? = null
) {
}