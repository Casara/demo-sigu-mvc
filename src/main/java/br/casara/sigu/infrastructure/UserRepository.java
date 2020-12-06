package br.casara.sigu.infrastructure;

import br.casara.sigu.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User> {

  @Query("SELECT user FROM User user ORDER BY user.name ASC")
  List<User> findAllOrderByName();

  boolean existsByCpf(String cpf);

  boolean existsByCpfAndIdIsNot(String cpf, UUID id);

}
