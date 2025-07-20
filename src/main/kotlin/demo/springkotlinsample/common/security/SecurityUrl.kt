package demo.springkotlinsample.common.security

object SecurityUrl {
    val PUBLIC_URLS = arrayOf("/auth/join", "/auth/login", "/docs", "/docs/**", "/swagger-ui/**")
    val ROLE_USER_URLS = arrayOf("/user/**")
    val ROLE_ADMIN_URLS = arrayOf("/admin/**")
}
