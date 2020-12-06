package br.casara.sigu.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@FieldNameConstants
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User extends Person {

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(
    name = "position_id",
    nullable = false,
    referencedColumnName = "id",
    foreignKey = @ForeignKey(
      value = ConstraintMode.CONSTRAINT,
      foreignKeyDefinition = "FOREIGN KEY (position_id) REFERENCES positions(id) ON UPDATE CASCADE ON DELETE RESTRICT"
    )
  )
  private Position position;

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "profile_user",
    joinColumns = @JoinColumn(
      name = "user_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(
        value = ConstraintMode.CONSTRAINT,
        foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE"
      )
    ),
    inverseJoinColumns = @JoinColumn(
      name = "profile_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(
        value = ConstraintMode.CONSTRAINT,
        foreignKeyDefinition = "FOREIGN KEY (profile_id) REFERENCES profiles(id) ON UPDATE CASCADE ON DELETE RESTRICT"
      )
    ),
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "profile_id"})
  )
  private List<Profile> profiles;
}
