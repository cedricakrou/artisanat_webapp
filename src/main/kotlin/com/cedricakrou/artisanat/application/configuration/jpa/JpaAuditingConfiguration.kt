package com.cedricakrou.artisanat.application.configuration.jpa

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaAuditingConfiguration : AuditorAwareImplementation() {

    @Bean
    fun auditorAware() : AuditorAware<String> = AuditorAwareImplementation()

}