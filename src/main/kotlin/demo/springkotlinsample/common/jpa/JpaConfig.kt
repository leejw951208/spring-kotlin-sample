package demo.springkotlinsample.common.jpa

import demo.springkotlinsample.common.security.SecurityUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class JpaConfig {
    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return AuditorAware { Optional.ofNullable(SecurityUtil.getUserId()) }
    }
}
