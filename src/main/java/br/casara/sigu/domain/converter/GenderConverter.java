package br.casara.sigu.domain.converter;

import br.casara.sigu.domain.enumeration.Gender;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
@NoArgsConstructor
public class GenderConverter implements AttributeConverter<Gender, String> {

  @Override
  public String convertToDatabaseColumn(final Gender attribute) {
    return attribute.getValue();
  }

  @Override
  public Gender convertToEntityAttribute(final String dbData) {
    return Gender.fromValue(dbData);
  }

}
