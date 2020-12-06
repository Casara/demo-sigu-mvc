package br.casara.sigu.web.problems;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class NotFoundProblem extends AbstractThrowableProblem {

  public NotFoundProblem(@Nullable final String detail) {
    super(null, Status.NOT_FOUND.getReasonPhrase(), Status.NOT_FOUND, detail,
      ServletUriComponentsBuilder.fromCurrentRequest().build().toUri());
  }

}
