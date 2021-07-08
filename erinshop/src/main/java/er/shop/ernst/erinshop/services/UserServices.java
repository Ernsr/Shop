package er.shop.ernst.erinshop.services;

import er.shop.ernst.erinshop.entities.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServices extends UserDetailsService {
//User
    Users checkUser(String email);
    Roles findRoleByName(String role);
    Users addUser(Users users);
    Users saveUser(Users users);





//NewItem
    NewItems checkNewItem(String string);
    NewItems addNewItem(NewItems newItem);
    NewItems saveNewItem(NewItems newItem);
    List<NewItems> getAllNewItem();
    NewItems getNewItem(Long id);
    void  deleteNewItem(NewItems newItem);


//T-Shirts
    Tshirts checkTshirts(String title);
    Tshirts addTshirts(Tshirts tshirts);
    Tshirts saveTshirts(Tshirts tshirts);
    List<Tshirts> getAllTshirts();
    Tshirts getTshirts(Long id);
    void deleteTshirts(Tshirts tshirts);

//Shirts
    Shirts checkShirts(String title);
    Shirts addShirts(Shirts shirts);
    Shirts saveShirts(Shirts shirts);
    List<Shirts> getAllShirts();
    Shirts getShirts(Long id);
    void deleteShirts(Shirts shirts);

//Sweetshirts
    Sweetshirts checkSweetshirt(String title);
    Sweetshirts addSweetshirt(Sweetshirts sweetshirts);
    Sweetshirts saveSweetshirt(Sweetshirts sweetshirts);
    List<Sweetshirts> getAllSweetshirt();
    Sweetshirts getSweetshirt(Long id);
    void deleteSweetshirt(Sweetshirts sweetshirts);

//Jacket
    Jackets checkJacket (String title);
    Jackets addJacket(Jackets jackets);
    Jackets saveJacket(Jackets jackets);
    List<Jackets> getAllJackets();
    Jackets getJacket(Long id);
    void deleteJacket(Jackets jackets);

//Short
    Shorts checkShort(String title);
    Shorts addShort(Shorts shorts);
    Shorts saveShort(Shorts shorts);
    List<Shorts> getAllShorts();
    Shorts getShorts(Long id);
    void deleteShort(Shorts shorts);











//Categories
    Categories checkCategory(String name);
    Categories addCategories(Categories categories);
    Categories saveCategories(Categories categories);
    List<Categories> getAllCategories();
    Categories getCategories(Long id);
    Categories getCategoriesById(Long id);
    void deleteCategories(Categories categories);

//City
    Cities checkCity(String name);
    Cities addCity(Cities cities);
    Cities saveCity(Cities cities);
    List<Cities> getAllCity();
    Cities getCity(Long id);
    void deleteCity(Cities cities);

}
