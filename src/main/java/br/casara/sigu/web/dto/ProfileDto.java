package br.casara.sigu.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@FieldNameConstants
public class ProfileDto {

  @NotBlank(message = "Obrigatório")
  private String name;

}
