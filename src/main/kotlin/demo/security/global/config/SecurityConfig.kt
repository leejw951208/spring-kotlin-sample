package demo.security.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import demo.security.global.JwtAuthenticationEntryPoint
import demo.security.global.jwt.JwtProperties
import demo.security.global.filter.JwtAuthenticationFilter
import demo.security.global.filter.JwtExceptionFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtProperties: JwtProperties
    , private val objectMapper: ObjectMapper
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .sessionManagement { sesstion: SessionManagementConfigurer<HttpSecurity> -> sesstion.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { formLogin: FormLoginConfigurer<HttpSecurity> -> formLogin.disable() }
            .httpBasic { httpBasic: HttpBasicConfigurer<HttpSecurity> -> httpBasic.disable() }
            .addFilterBefore(JwtAuthenticationFilter(jwtProperties), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(JwtExceptionFilter(objectMapper), JwtAuthenticationFilter::class.java)
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/").permitAll()
            }
            .exceptionHandling { exception -> exception.authenticationEntryPoint(JwtAuthenticationEntryPoint()) }
        return http.build();
    }
}