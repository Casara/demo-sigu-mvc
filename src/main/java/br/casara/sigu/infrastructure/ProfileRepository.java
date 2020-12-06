package br.casara.sigu.infrastructure;

import br.casara.sigu.domain.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends BaseRepository<Profile> {

  @Query("SELECT profile FROM Profile profile ORDER BY profile.name ASC")
  List<Profile> findAllOrderByName();

}
