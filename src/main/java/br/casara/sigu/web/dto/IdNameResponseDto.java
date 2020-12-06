package br.casara.sigu.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class IdNameResponseDto {

  private UUID id;

  private String name;

}
