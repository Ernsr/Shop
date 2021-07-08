package er.shop.ernst.erinshop.repositories;

import er.shop.ernst.erinshop.entities.Categories;
import er.shop.ernst.erinshop.entities.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    Categories findByName (String name);
}
