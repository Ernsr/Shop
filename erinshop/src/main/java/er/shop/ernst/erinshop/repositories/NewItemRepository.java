package er.shop.ernst.erinshop.repositories;

import er.shop.ernst.erinshop.entities.NewItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface NewItemRepository extends JpaRepository<NewItems, Long> {

    NewItems findByTitle (String title);

}
