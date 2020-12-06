package br.casara.sigu.infrastructure;

import br.casara.sigu.domain.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends BaseRepository<Position> {

  @Query("SELECT position FROM Position position ORDER BY position.name ASC")
  List<Position> findAllOrderByName();

}
