package br.casara.sigu.web.problems;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class DeleteRestrictionProblem extends AbstractThrowableProblem {

  public DeleteRestrictionProblem(@Nullable final String detail) {
    super(null, Status.UNPROCESSABLE_ENTITY.getReasonPhrase(), Status.UNPROCESSABLE_ENTITY, detail,
      ServletUriComponentsBuilder.fromCurrentRequest().build().toUri());
  }

}
