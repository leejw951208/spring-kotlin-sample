package demo.kotlinboilerplate.common.config

import demo.kotlinboilerplate.common.util.SecurityUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
//class PersistenceConfig {
//    @Bean
//    fun auditorProvider(): AuditorAware<Long> =
//        AuditorAware { Optional.of(SecurityUtil.getCurrentUserId()) }
//}