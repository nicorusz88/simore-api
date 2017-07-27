package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {


    Role findByName(String name);
}
