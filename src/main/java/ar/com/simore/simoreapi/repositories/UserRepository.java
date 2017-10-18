package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String userName);

    List<User> findByRoles_Name(String role);

    List<User> findByRoles_NameOrRoles_NameOrRoles_Name(String role1, String role2, String role3);

    List<User> findByProfessional(User professional);
}
