package er.shop.ernst.erinshop.repositories;

import er.shop.ernst.erinshop.entities.Roles;
import er.shop.ernst.erinshop.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

}
