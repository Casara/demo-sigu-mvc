package br.casara.sigu.domain;

import br.casara.sigu.domain.converter.GenderConverter;
import br.casara.sigu.domain.enumeration.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@FieldNameConstants
public abstract class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "org.hibernate.type.PostgresUUIDType")
  @Column(updatable = false, nullable = false, unique = true)
  @Builder.Default
  private UUID id = UUID.randomUUID();

  @Column(nullable = false, unique = true)
  private String name;

  @Column(length = 11, nullable = false, unique = true)
  private String cpf;

  private LocalDate birthDate;

  @Column(length = 1)
  @Convert(converter = GenderConverter.class)
  private Gender gender;

}
