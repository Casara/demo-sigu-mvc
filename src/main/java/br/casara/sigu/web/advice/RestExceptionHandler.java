package br.casara.sigu.web.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class RestExceptionHandler implements ProblemHandling {

  @Value("${problem.with-stack-traces}")
  private boolean withStackTraces;

  @Override
  public boolean isCausalChainsEnabled() {
    return this.withStackTraces;
  }

}
