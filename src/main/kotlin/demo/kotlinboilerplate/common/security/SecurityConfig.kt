package demo.kotlinboilerplate.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import demo.kotlinboilerplate.auth.token.TokenProperties
import demo.kotlinboilerplate.auth.token.TokenProvider
import demo.kotlinboilerplate.common.security.exception.CustomAccessDeniedHandler
import demo.kotlinboilerplate.common.security.exception.CustomAuthenticationEntryPoint
import demo.kotlinboilerplate.common.security.filter.CustomExceptionFilter
import demo.kotlinboilerplate.common.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.util.PathMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProperties: TokenProperties,
    private val tokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        introspector: HandlerMappingIntrospector,
        pathMatcher: PathMatcher,
    ): SecurityFilterChain {
        http.csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .addFilterBefore(
                JwtAuthenticationFilter(tokenProperties, tokenProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .addFilterBefore(CustomExceptionFilter(objectMapper), JwtAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it.requestMatchers(*SecurityUrl.PUBLIC_URL_ARRAY).permitAll()
                it.requestMatchers(*SecurityUrl.ROLE_USER_URL_ARRAY).hasRole("USER")
                it.anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))
                it.accessDeniedHandler(CustomAccessDeniedHandler(objectMapper))
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
