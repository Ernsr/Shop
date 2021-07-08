package er.shop.ernst.erinshop.repositories;

import er.shop.ernst.erinshop.entities.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CityesRepository extends JpaRepository<Cities, Long> {

    Cities findByName (String name);

}
