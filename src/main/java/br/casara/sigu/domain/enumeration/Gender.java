package br.casara.sigu.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Gender {

  FEMALE("F"),
  MALE("M")
  ;

  @JsonValue
  private final String value;

  public static Gender fromValue(final String value) {
    return Optional.ofNullable(value).flatMap(s -> Stream.of(values())
      .filter(enumValue -> enumValue.getValue().equals(value))
      .findFirst())
      .orElse(null);
  }

}
