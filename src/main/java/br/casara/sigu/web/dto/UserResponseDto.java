package br.casara.sigu.web.dto;

import br.casara.sigu.domain.enumeration.Gender;
import br.casara.sigu.web.serializer.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResponseDto {

  private UUID id;

  private String name;

  private String cpf;

  @JsonSerialize(using = CustomLocalDateSerializer.class)
  private LocalDate birthDate;

  private Gender gender;

  private IdNameResponseDto position;

  private List<IdNameResponseDto> profiles;

}
