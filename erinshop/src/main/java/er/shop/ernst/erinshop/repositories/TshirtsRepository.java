package er.shop.ernst.erinshop.repositories;

import er.shop.ernst.erinshop.entities.Tshirts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TshirtsRepository extends JpaRepository<Tshirts, Long> {

    Tshirts findByTitle (String title);

}
