package br.casara.sigu.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, UUID> {

  boolean existsByName(String name);

  boolean existsByNameAndIdIsNot(String name, UUID id);

}
