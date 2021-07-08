package er.shop.ernst.erinshop.services.impl;

import er.shop.ernst.erinshop.entities.*;
import er.shop.ernst.erinshop.repositories.*;
import er.shop.ernst.erinshop.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private CityesRepository cityesRepository;

    @Autowired
    private TshirtsRepository tshirtsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ShirtsRepository shirtsRepository;

    @Autowired
    private NewItemRepository newItemRepository;

    @Autowired
    private SweetshirtRepository sweetshirtRepository;

    @Autowired
    private JacketRepository jacketRepository;

    @Autowired
    private ShortsRepository shortsRepository;

//User
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(s);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @Override
    public Users checkUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Roles findRoleByName(String role) {
        return rolesRepository.findByRole(role);
    }

    @Override
    public Users addUser(Users user){
        return userRepository.save(user);
    }

    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }




//NewItem

    @Override
    public NewItems checkNewItem(String title) {
        return newItemRepository.findByTitle(title);
    }

    @Override
    public NewItems addNewItem(NewItems newItem) {
        return newItemRepository.save(newItem);
    }

    @Override
    public NewItems saveNewItem(NewItems newItem) {
        return newItemRepository.save(newItem);
    }

    @Override
    public List<NewItems> getAllNewItem() {
        return newItemRepository.findAll();
    }

    @Override
    public NewItems getNewItem(Long id) {
        Optional<NewItems> opt = newItemRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void deleteNewItem(NewItems newItem) {
        newItemRepository.delete(newItem);
    }



//T-Shirts
    @Override
    public Tshirts checkTshirts(String title) {
        return tshirtsRepository.findByTitle(title);
    }

    @Override
    public Tshirts addTshirts(Tshirts tshirts) {
        return tshirtsRepository.save(tshirts);
    }

    @Override
    public Tshirts saveTshirts(Tshirts tshirts) {
        return tshirtsRepository.save(tshirts);
    }

    @Override
    public List<Tshirts> getAllTshirts() {
        return tshirtsRepository.findAll();
    }

    @Override
    public Tshirts getTshirts(Long id) {
        Optional<Tshirts> opt = tshirtsRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void deleteTshirts(Tshirts tshirts) {
        tshirtsRepository.delete(tshirts);
    }



//Shirts
    @Override
    public Shirts checkShirts(String title) {
        return shirtsRepository.findByTitle(title);
    }

    @Override
    public Shirts addShirts(Shirts shirts) {
        return shirtsRepository.save(shirts);
    }

    @Override
    public Shirts saveShirts(Shirts shirts) {
        return shirtsRepository.save(shirts);
    }

    @Override
    public List<Shirts> getAllShirts() {
        return shirtsRepository.findAll();
    }

    @Override
    public Shirts getShirts(Long id) {
        Optional<Shirts> opt = shirtsRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void deleteShirts(Shirts shirts) {
         shirtsRepository.delete(shirts);
    }


//SweetShirts
    @Override
    public Sweetshirts checkSweetshirt(String title) {
        return sweetshirtRepository.findByTitle(title);
    }

    @Override
    public Sweetshirts addSweetshirt(Sweetshirts sweetshirts) {
        return sweetshirtRepository.save(sweetshirts);
    }

    @Override
    public Sweetshirts saveSweetshirt(Sweetshirts sweetshirts) {
        return sweetshirtRepository.save(sweetshirts);
    }

    @Override
    public List<Sweetshirts> getAllSweetshirt() {
        return sweetshirtRepository.findAll();
    }

    @Override
    public Sweetshirts getSweetshirt(Long id) {
        Optional<Sweetshirts> opt = sweetshirtRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void deleteSweetshirt(Sweetshirts sweetshirts) {
        sweetshirtRepository.delete(sweetshirts);
    }


//Jacket
    @Override
    public Jackets checkJacket(String title) {
        return jacketRepository.findByTitle(title);
    }

    @Override
    public Jackets addJacket(Jackets jackets) {
        return jacketRepository.save(jackets);
    }

    @Override
    public Jackets saveJacket(Jackets jackets) {
        return jacketRepository.save(jackets);
    }

    @Override
    public List<Jackets> getAllJackets() {
        return jacketRepository.findAll();
    }

    @Override
    public Jackets getJacket(Long id) {
        Optional<Jackets> opt = jacketRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void deleteJacket(Jackets jackets) {
        jacketRepository.delete(jackets);
    }


//Shorts
    @Override
    public Shorts checkShort(String title) {
        return shortsRepository.findByTitle(title);
    }

    @Override
    public Shorts addShort(Shorts shorts) {
        return shortsRepository.save(shorts);
    }

    @Override
    public Shorts saveShort(Shorts shorts) {
        return shortsRepository.save(shorts);
    }

    @Override
    public List<Shorts> getAllShorts() {
        return shortsRepository.findAll();
    }

    @Override
    public Shorts getShorts(Long id) {
        Optional<Shorts> opt = shortsRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void deleteShort(Shorts shorts) {
        shortsRepository.delete(shorts);
    }


//Category
    @Override
    public Categories checkCategory(String name) {
        return categoriesRepository.findByName(name);
    }

    @Override
    public Categories addCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public Categories saveCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }


    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories getCategories(Long id) {
        Optional<Categories> opt = categoriesRepository.findById(id);
        return  opt.orElse(null);
    }

    @Override
    public Categories getCategoriesById(Long id) {
        Optional <Categories> opt = categoriesRepository.findById(id);
        return  opt.orElse(null);
    }

    @Override
    public void deleteCategories(Categories categories) {
        categoriesRepository.delete(categories);
    }


    //City
    @Override
    public Cities checkCity(String name) {
        return cityesRepository.findByName(name);
    }

    @Override
    public Cities addCity(Cities cities) {
        return cityesRepository.save(cities);
    }

    @Override
    public Cities saveCity(Cities cities) {
        return cityesRepository.save(cities);
    }

    @Override
    public List<Cities> getAllCity() {
        return cityesRepository.findAll();
    }

    @Override
    public Cities getCity(Long id) {
        Optional<Cities> opt = cityesRepository.findById(id);
        return  opt.orElse(null);
    }

    @Override
    public void deleteCity(Cities cities) {
        cityesRepository.delete(cities);
    }


}
