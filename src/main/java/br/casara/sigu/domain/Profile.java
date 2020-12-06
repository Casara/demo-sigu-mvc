package br.casara.sigu.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldNameConstants
@Table(name = "PROFILES")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "org.hibernate.type.PostgresUUIDType")
  @Column(updatable = false, nullable = false, unique = true)
  @Builder.Default
  private UUID id = UUID.randomUUID();

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "profiles")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private List<User> users;
}
