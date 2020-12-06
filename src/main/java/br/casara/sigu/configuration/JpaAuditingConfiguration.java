package br.casara.sigu.configuration;

import br.casara.sigu.infrastructure.BaseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackageClasses = BaseRepository.class)
public class JpaAuditingConfiguration {

  @Bean
  public AuditorAware<UUID> auditorProvider() {
    return Optional::empty;
  }

}
