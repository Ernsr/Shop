package er.shop.ernst.erinshop.repositories;

import er.shop.ernst.erinshop.entities.Shorts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShortsRepository extends JpaRepository<Shorts, Long> {

    Shorts findByTitle(String title);

}
