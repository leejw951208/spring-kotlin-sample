package demo.springkotlinsample.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import demo.springkotlinsample.auth.token.TokenProperties
import demo.springkotlinsample.auth.token.TokenProvider
import demo.springkotlinsample.common.security.exception.CustomAccessDeniedHandler
import demo.springkotlinsample.common.security.exception.CustomAuthenticationEntryPoint
import demo.springkotlinsample.common.security.filter.CustomExceptionFilter
import demo.springkotlinsample.common.security.filter.JwtAuthenticationFilter
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
            .authorizeHttpRequests {
                it.requestMatchers(*SecurityUrl.PUBLIC_URLS).permitAll()
                it.requestMatchers(*SecurityUrl.ROLE_USER_URLS).hasRole("USER")
                it.anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))
                it.accessDeniedHandler(CustomAccessDeniedHandler(objectMapper))
            }
            .addFilterBefore(
                JwtAuthenticationFilter(tokenProperties, tokenProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .addFilterBefore(CustomExceptionFilter(objectMapper), JwtAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
