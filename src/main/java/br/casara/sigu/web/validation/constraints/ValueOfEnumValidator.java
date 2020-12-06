package br.casara.sigu.web.validation.constraints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@NoArgsConstructor
public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

  private ValueOfEnum constraintAnnotation;

  @Override
  public void initialize(final ValueOfEnum constraintAnnotation) {
    this.constraintAnnotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(final CharSequence charSequence, final ConstraintValidatorContext context) {
    return Optional.ofNullable(charSequence).map(value -> {
      final var enumValues = this.constraintAnnotation.enumClass().getEnumConstants();
      final var writer = new ObjectMapper().writerFor(this.constraintAnnotation.enumClass());
      return Stream.of(enumValues).anyMatch(enumConstant -> {
        try {
          final var enumValue = writer.writeValueAsString(enumConstant).replace("\"", "");
          return enumValue.contentEquals(value)
            || this.constraintAnnotation.ignoreCase() && enumValue.equalsIgnoreCase(value.toString());
        } catch (JsonProcessingException e) {
          if (log.isErrorEnabled()) {
            log.error("Error on serialize enum constant: {0}", e);
          }
          return false;
        }
      });
    }).orElse(true);
  }

}
