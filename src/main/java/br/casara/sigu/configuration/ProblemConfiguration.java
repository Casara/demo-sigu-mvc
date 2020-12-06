package br.casara.sigu.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class ProblemConfiguration {

  @Value("${problem.with-stack-traces}")
  private boolean withStackTraces;

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().registerModules(
      new ProblemModule().withStackTraces(this.withStackTraces),
      new ConstraintViolationProblemModule());
  }

}
