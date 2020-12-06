package br.casara.sigu.web.dto;

import br.casara.sigu.domain.enumeration.Gender;
import br.casara.sigu.web.validation.ValidationConstants;
import br.casara.sigu.web.validation.constraints.ValueOfEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@FieldNameConstants
public class UserDto {

  @NotBlank(message = "Obrigatório")
  private String name;

  @NotBlank(message = "Obrigatório")
  @CPF(message = "CPF inválido")
  private String cpf;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate birthDate;

  @NotBlank(message = "Obrigatório")
  @ValueOfEnum(enumClass = Gender.class, message = "Sexo inválido")
  private String gender;

  @NotBlank(message = "Obrigatório")
  @Pattern(regexp = ValidationConstants.UUID_PATTERN, message = "ID inválido")
  private String positionId;

  private List<String> profiles;

}
