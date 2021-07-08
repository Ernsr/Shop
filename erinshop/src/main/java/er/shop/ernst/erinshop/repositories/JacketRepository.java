package er.shop.ernst.erinshop.repositories;

import er.shop.ernst.erinshop.entities.Jackets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface JacketRepository extends JpaRepository<Jackets, Long> {

    Jackets findByTitle(String title);

}
