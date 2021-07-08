package er.shop.ernst.erinshop.controllers;


import er.shop.ernst.erinshop.entities.*;
import er.shop.ernst.erinshop.services.UserServices;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${loadUrl}")
    private String loadURL;

    @Value("${targetUrl}")
    private String targetURL;


    @Value("${imageTargetUrl}")
    private String imageTargetUrl;

    @GetMapping(value = "/adminpage")
    public String adminPage(Model model) {

        List<Cities> city = userServices.getAllCity();
        model.addAttribute("city", city);

        model.addAttribute("CURRENT_USER", getUser());
        return "admin";
    }





//New Item
    @GetMapping(value = "/addNewItem")
    public String addnewItem(Model model) {

        List<NewItems> newitems = userServices.getAllNewItem();
        model.addAttribute("newitem", newitems);

        return "addNewItem";
    }

    @PostMapping(value = "/addnewitem")
    public String addNewItem(@RequestParam(name = "newitem_title") String newItemTitle,
                             @RequestParam(name = "newitem_name") String newItemName,
                             @RequestParam(name = "newitem_price") String newItemPrice) {
        NewItems newItem = userServices.checkNewItem(newItemTitle);

        if (newItem == null) {
            NewItems newItems = new NewItems();
            newItems.setTitle(newItemTitle);
            newItems.setName(newItemName);
            newItems.setPrice(newItemPrice);

            userServices.addNewItem(newItems);

            return "redirect:/admin/addNewItem";
        }
        return "redirect:/admin/addNewItem?error";
    }

    @GetMapping(value = "/detailsnewitem/{newitemId}")
    public String newItemDetails(Model model, @PathVariable(name = "newitemId") Long id) {

        NewItems newitem = userServices.getNewItem(id);
        model.addAttribute("newitem", newitem);

        List<Categories> categories = userServices.getAllCategories();

        categories.removeAll(newitem.getCategories());

        model.addAttribute("categories", categories);

        return "detailsnewitem";
    }

    @PostMapping(value = "/savenewitem")
    public String saveNewItem(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "newitem_title") String newitemTitle,
                             @RequestParam(name = "newitem_name") String newitemName,
                             @RequestParam(name = "newitem_price") String newitemPrice) {

        NewItems newItem = userServices.getNewItem(id);

        if (newItem != null) {
            newItem.setTitle(newitemTitle);
            newItem.setName(newitemName);
            newItem.setPrice(newitemPrice);

            userServices.saveNewItem(newItem);

            return "redirect:/admin/addNewItem";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deletenewitem")
    public String deleteNewItem(@RequestParam(name = "id") Long id) {

        NewItems newItem = userServices.getNewItem(id);
        if (newItem != null) {
            userServices.deleteNewItem(newItem);
        }
        return "redirect:/admin/addNewItem";
    }



//T-Shirts
    @GetMapping(value = "/addTshirts")
    public String addTshirts(Model model) {

        List<Tshirts> tshirts = userServices.getAllTshirts();
        model.addAttribute("tshirts", tshirts);

        return "addTshirts";
    }

    @PostMapping(value = "/addtshirts")
    public String addTshirt(@RequestParam(name = "tshirt_title") String tshirtTitle,
                            @RequestParam(name = "tshirt_name") String tshirtName,
                            @RequestParam(name = "tshirt_price") String tshirtPrice) {

        Tshirts tshirts = userServices.checkTshirts(tshirtTitle);

        if (tshirts == null) {

            Tshirts newTshirts = new Tshirts();

            newTshirts.setTitle(tshirtTitle);
            newTshirts.setName(tshirtName);
            newTshirts.setPrice(tshirtPrice);

            userServices.addTshirts(newTshirts);

            return "redirect:/admin/addTshirts";
        }
        return "redirect:/admin/addTshirts?error";
    }

    @GetMapping(value = "/detailstshirt/{tshirtId}")
    public String tshirtDetails(Model model, @PathVariable(name = "tshirtId") Long id) {

        Tshirts tshirts = userServices.getTshirts(id);
        model.addAttribute("tshirts", tshirts);

        List<Categories> categories = userServices.getAllCategories();

        categories.removeAll(tshirts.getCategories());

        model.addAttribute("categories", categories);

        return "detailstshirt";
    }

    @PostMapping(value = "/savetshirt")
    public String saveTshirt(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "tshirt_title") String tshirtTitle,
                             @RequestParam(name = "tshirt_name") String tshirtName,
                             @RequestParam(name = "tshirt_price") String tshirtPrice) {

        Tshirts tshirts = userServices.getTshirts(id);

        if (tshirts != null) {
            tshirts.setTitle(tshirtTitle);
            tshirts.setName(tshirtName);
            tshirts.setPrice(tshirtPrice);

            userServices.saveTshirts(tshirts);

            return "redirect:/admin/addTshirts";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deletetshirt")
    public String deleteTshirts(@RequestParam(name = "id") Long id) {

        Tshirts tshirts = userServices.getTshirts(id);
        if (tshirts != null) {
            userServices.deleteTshirts(tshirts);
        }
        return "redirect:/";
    }




//Shirts
    @GetMapping(value = "/addShirts")
    public String addShirts(Model model) {

        List<Shirts> shirts = userServices.getAllShirts();
        model.addAttribute("shirts", shirts);


        return "addShirts";
    }

    @PostMapping(value = "/addshirts")
    public String addShirt(@RequestParam(name = "shirt_title") String shirtTitle,
                            @RequestParam(name = "shirt_name") String shirtName,
                            @RequestParam(name = "shirt_price") String shirtPrice) {

        Shirts shirts = userServices.checkShirts(shirtTitle);

        if (shirts == null) {

            Shirts newShirts = new Shirts();
            newShirts.setTitle(shirtTitle);
            newShirts.setName(shirtName);
            newShirts.setPrice(shirtPrice);

            userServices.addShirts(newShirts);

            return "redirect:/admin/addShirts";
        }
        return "redirect:/admin/addShirts?error";
    }


    @GetMapping(value = "/detailsshirt/{shirtsId}")
    public String shirtDetails(Model model, @PathVariable(name = "shirtsId") Long id) {

        Shirts shirts = userServices.getShirts(id);
        model.addAttribute("shirts", shirts);

        List<Categories> categories = userServices.getAllCategories();

        categories.removeAll(shirts.getCategories());

        model.addAttribute("categories", categories);

        return "detailsshirt";
    }


    @PostMapping(value = "/saveshirt")
    public String saveShirt(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "shirt_title") String shirtTitle,
                             @RequestParam(name = "shirt_name") String shirtName,
                             @RequestParam(name = "shirt_price") String shirtPrice) {

        Shirts shirts = userServices.getShirts(id);

        if (shirts != null) {
            shirts.setTitle(shirtTitle);
            shirts.setName(shirtName);
            shirts.setPrice(shirtPrice);

            userServices.saveShirts(shirts);

            return "redirect:/admin/addShirts";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deleteshirt")
    public String deleteShirts(@RequestParam(name = "id") Long id) {

        Shirts shirts = userServices.getShirts(id);
        if (shirts != null) {
            userServices.deleteShirts(shirts);
        }
        return "redirect:/";
    }




//SweetShirt
    @GetMapping(value = "/addSweetshirts")
    public String addSweetshirts(Model model) {

        List<Sweetshirts> sweetshirts = userServices.getAllSweetshirt();
        model.addAttribute("sweetshirts", sweetshirts);

        return "addSweetshirts";
    }

    @PostMapping(value = "/addsweetshirt")
    public String addSweetShirt(@RequestParam(name = "sweetshirt_title") String sweetShirtTitle,
                           @RequestParam(name = "sweetshirt_name") String sweetShirtName,
                           @RequestParam(name = "sweetshirt_price") String sweetShirtPrice) {

        Sweetshirts sweetshirts = userServices.checkSweetshirt(sweetShirtTitle);

        if (sweetshirts == null) {

            Sweetshirts newSweetshirts = new Sweetshirts();
            newSweetshirts.setTitle(sweetShirtTitle);
            newSweetshirts.setName(sweetShirtName);
            newSweetshirts.setPrice(sweetShirtPrice);

            userServices.addSweetshirt(newSweetshirts);

            return "redirect:/admin/addSweetshirts";
        }
        return "redirect:/admin/addSweetshirts?error";
    }

    @GetMapping(value = "/detailssweetshirt/{sweetshirtId}")
    public String sweetShirtDetails(Model model, @PathVariable(name = "sweetshirtId") Long id) {

        Sweetshirts sweetshirts = userServices.getSweetshirt(id);
        model.addAttribute("sweetshirts", sweetshirts);

        List<Categories> categories = userServices.getAllCategories();
        categories.removeAll(sweetshirts.getCategories());
        model.addAttribute("categories", categories);

        return "detailssweetshirt";
    }

    @PostMapping(value = "/savesweetshirt")
    public String saveSweetshirt(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "sweetshirt_title") String sweetshirtTitle,
                                 @RequestParam(name = "sweetshirt_name") String sweetshirtName,
                                 @RequestParam(name = "sweetshirt_price") String sweetshirtPrice) {

        Sweetshirts sweetshirts = userServices.getSweetshirt(id);

        if (sweetshirts != null) {
            sweetshirts.setTitle(sweetshirtTitle);
            sweetshirts.setName(sweetshirtName);
            sweetshirts.setPrice(sweetshirtPrice);

            userServices.saveSweetshirt(sweetshirts);

            return "redirect:/admin/addSweetshirts";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deletesweetshirt")
    public String deleteSweetshirt(@RequestParam(name = "id") Long id) {

        Sweetshirts sweetshirts = userServices.getSweetshirt(id);

        if (sweetshirts != null) {
            userServices.deleteSweetshirt(sweetshirts);
        }
        return "redirect:/";
    }


//Jacket
    @GetMapping(value = "/addJackets")
    public String addJackets(Model model) {

        List<Jackets> jackets = userServices.getAllJackets();
        model.addAttribute("jackets", jackets);

        return "addJackets";
    }

    @PostMapping(value = "/addjackets")
    public String addJackets(@RequestParam(name = "jacket_title") String jacketTitle,
                             @RequestParam(name = "jacket_name") String jacketName,
                             @RequestParam(name = "jacket_price") String jacketPrice) {

        Jackets jackets = userServices.checkJacket(jacketTitle);

        if (jackets == null) {
            Jackets newJackets = new Jackets();
            newJackets.setTitle(jacketTitle);
            newJackets.setName(jacketName);
            newJackets.setPrice(jacketPrice);

            userServices.addJacket(newJackets);

            return "redirect:/admin/addJackets/";
        }
        return "redirect:/admin/addJackets?error";
    }

    @GetMapping(value = "/detailsjacket/{jacketId}")
    public String detailsJacket(Model model, @PathVariable(name = "jacketId") Long id) {

        Jackets jackets = userServices.getJacket(id);
        model.addAttribute("jackets", jackets);

        List<Categories> categories = userServices.getAllCategories();
        categories.removeAll(jackets.getCategories());
        model.addAttribute("categories", categories);

        return "detailsjacket";
    }

    @PostMapping(value = "/savejacket")
    public String saveJacket(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "jacket_title") String jacketTitle,
                             @RequestParam(name = "jacket_name") String jacketName,
                             @RequestParam(name = "jacket_price") String jacketPrice) {

        Jackets jackets = userServices.getJacket(id);

        if (jackets != null) {
            jackets.setTitle(jacketTitle);
            jackets.setName(jacketName);
            jackets.setPrice(jacketPrice);


            userServices.saveJacket(jackets);

            return "redirect:/admin/detailsjacket/"+id;
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deletejacket")
    public String deleteJacket(@RequestParam(name = "id") Long id) {

        Jackets jackets = userServices.getJacket(id);

        if (jackets != null) {
            userServices.deleteJacket(jackets);
        }
        return "redirect:/addJackets/";
    }


//Shorts
    @GetMapping(value = "/addShorts")
    public String addShorts(Model model) {

        List<Shorts> shorts = userServices.getAllShorts();
        model.addAttribute("shorts", shorts);

        return "addShorts";
    }

    @PostMapping(value = "/addshort")
    public String addShorts(@RequestParam(name = "short_title") String shortTitle,
                            @RequestParam(name = "short_name") String shortName,
                            @RequestParam(name = "short_price") String shortPrice) {

        Shorts shorts = userServices.checkShort(shortTitle);

        if (shorts == null) {
            Shorts newshorts = new Shorts();
            newshorts.setTitle(shortTitle);
            newshorts.setName(shortName);
            newshorts.setPrice(shortPrice);

            userServices.saveShort(newshorts);

            return "redirect:/admin/addShorts/";
        }
        return "redirect:/admin/addShorts?error";
    }

    @GetMapping(value = "/detailsshort/{shortId}")
    public String detailsShort(Model model, @PathVariable(name = "shortId") Long id) {

        Shorts shorts = userServices.getShorts(id);
        model.addAttribute("shorts", shorts);

        List<Categories> categories = userServices.getAllCategories();
        categories.removeAll(shorts.getCategories());
        model.addAttribute("categories", categories);

        return "/detailsshort";

    }

    @PostMapping(value = "/saveshort")
    public String saveShort(@RequestParam(name = "id") Long id,
                            @RequestParam(name = "short_title") String shortTitle,
                            @RequestParam(name = "short_name") String shortName,
                            @RequestParam(name = "short_price") String shortPrice) {
        Shorts shorts = userServices.getShorts(id);

        if (shorts!=null) {
            shorts.setTitle(shortTitle);
            shorts.setName(shortName);
            shorts.setPrice(shortPrice);

            userServices.saveShort(shorts);

            return "redirect:/admin/detailsshort/"+id;
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deleteshort")
    public String deleteShort(@RequestParam(name = "id") Long id) {

        Shorts shorts = userServices.getShorts(id);

        if (shorts != null) {
            userServices.deleteShort(shorts);
        }
        return "redirect:/addShort/";
    }







//Category
    @GetMapping(value = "/addcategory")
    public String addcategory(Model model) {

        List<Categories> categories = userServices.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("CURRENT_USER", getUser());
        return "addcategory";
    }

    @PostMapping(value = "/savecategory")
    public String saveCategory(@RequestParam(name = "id") Long id,
                               @RequestParam(name = "category_name") String categoryName) {

        Categories categories = userServices.getCategories(id);

        if (categories != null) {
            categories.setName(categoryName);

            userServices.saveCategories(categories);

            return "redirect:/admin/addcategory/";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/addcategory")
    public String addCategory(@RequestParam(name = "category_name") String categoryName) {

        Categories categories = userServices.checkCategory(categoryName);

        if (categories == null) {

            Categories newCategories = new Categories();
            newCategories.setName(categoryName);

            userServices.addCategories(newCategories);

            return "redirect:/admin/addcategory";
        }
        return "redirect:/admin/addcategories?error";
    }

    @PostMapping(value = "/deletecategory")
    public String deleteCategory(@RequestParam(name = "id") Long id) {

        Categories categories = userServices.getCategories(id);
        if (categories != null) {
            userServices.deleteCategories(categories);
        }
        return "redirect:/addcategory";
    }

    @GetMapping(value = "/detailscategori/{categoryId}")
    public String categoryDetails(Model model, @PathVariable(name = "categoryId") Long id) {
        Categories categories = userServices.getCategories(id);
        model.addAttribute("categories", categories);

        return "detailscategory";
    }






//NEW-ITEM
    @PostMapping(value = "/newitemassigncategory")
    public String newitemassigncategory(@RequestParam(name = "category_id") Long categoryId,
                                        @RequestParam(name = "newitem_id") Long newitemId){
        NewItems newItems = userServices.getNewItem(newitemId);
        if (newItems != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = newItems.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (!categories.contains(category)) {
                    categories.add(category);
                    newItems.setCategories(categories);
                    userServices.saveNewItem(newItems);
                }
                return "redirect:/admin/detailsnewitem/" + newitemId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/newitemremovecategory")
    public String newitemremovecategory(@RequestParam(name = "category_id") Long categoryId,
                                        @RequestParam(name = "newitem_id") Long newitemId){
        NewItems newItems = userServices.getNewItem(newitemId);
        if (newItems != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = newItems.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (categories.contains(category)) {
                    categories.remove(category);
                    newItems.setCategories(categories);
                    userServices.saveNewItem(newItems);
                }
                return "redirect:/admin/detailsnewitem/" + newitemId;
            }
        }
        return "redirect:/";
    }

//T-SHIRTS
    @PostMapping(value = "/assigncategory")
    public String assigncategory(@RequestParam(name = "category_id") Long categoryId,
                                 @RequestParam(name = "tshirt_id") Long tshirtId){
        Tshirts tshirt = userServices.getTshirts(tshirtId);
        if (tshirt != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = tshirt.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (!categories.contains(category)) {
                    categories.add(category);
                    tshirt.setCategories(categories);
                    userServices.saveTshirts(tshirt);
                }
                return "redirect:/admin/detailstshirt/" + tshirtId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/removecategory")
    public String removecategory(@RequestParam(name = "category_id") Long categoryId,
                                 @RequestParam(name = "tshirt_id") Long tshirtId){
        Tshirts tshirt = userServices.getTshirts(tshirtId);
        if (tshirt != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = tshirt.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (categories.contains(category)) {
                    categories.remove(category);
                    tshirt.setCategories(categories);
                    userServices.saveTshirts(tshirt);
                }
                return "redirect:/admin/detailstshirt/" + tshirtId;
            }
        }
        return "redirect:/";
    }

//Shirt
    @PostMapping(value = "/shirtassigncategory")
    public String shirtassigncategory(@RequestParam(name = "category_id") Long categoryId,
                                      @RequestParam(name = "shirts_id") Long shirtsId){
        Shirts shirts = userServices.getShirts(shirtsId);
        if (shirts != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = shirts.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (!categories.contains(category)) {
                    categories.add(category);
                    shirts.setCategories(categories);
                    userServices.saveShirts(shirts);
                }
                return "redirect:/admin/detailsshirt/" + shirtsId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/shirtremovecategory")
    public String shirtremovecategory(@RequestParam(name = "category_id") Long categoryId,
                                      @RequestParam(name = "shirts_id") Long shirtsId){
        Shirts shirts = userServices.getShirts(shirtsId);
        if (shirts != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = shirts.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (categories.contains(category)) {
                    categories.remove(category);
                    shirts.setCategories(categories);
                    userServices.saveShirts(shirts);
                }
                return "redirect:/admin/detailsshirt/" + shirtsId;
            }
        }
        return "redirect:/";
    }

// SWEET-SHIRT
    @PostMapping(value = "/sweetshirtassigncategory")
    public String sweetshirtassigncategory(@RequestParam(name = "category_id") Long categoryId,
                                           @RequestParam(name = "sweetshirts_id") Long sweetshirtId){
        Sweetshirts sweetshirts = userServices.getSweetshirt(sweetshirtId);
        if (sweetshirts != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = sweetshirts.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (!categories.contains(category)) {
                    categories.add(category);
                    sweetshirts.setCategories(categories);
                    userServices.saveSweetshirt(sweetshirts);
                }
                return "redirect:/admin/detailssweetshirt/" + sweetshirtId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/sweetshirtremovecategory")
    public String sweetshirtremovecategory(@RequestParam(name = "category_id") Long categoryId,
                                           @RequestParam(name = "sweetshirts_id") Long sweetshirtId){
        Sweetshirts sweetshirts = userServices.getSweetshirt(sweetshirtId);
        if (sweetshirts != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = sweetshirts.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (categories.contains(category)) {
                    categories.remove(category);
                    sweetshirts.setCategories(categories);
                    userServices.saveSweetshirt(sweetshirts);
                }
                return "redirect:/admin/detailssweetshirt/" + sweetshirtId;
            }
        }
        return "redirect:/";
    }

//Jacket
    @PostMapping(value = "/jacketassigncategory")
    public String jacketassigncategory(@RequestParam(name = "category_id") Long categoryId,
                                       @RequestParam(name = "jacket_id") Long jacketId){
        Jackets jackets = userServices.getJacket(jacketId);
        if (jackets != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = jackets.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (!categories.contains(category)) {
                    categories.add(category);
                    jackets.setCategories(categories);
                    userServices.saveJacket(jackets);
                }

                return "redirect:/admin/detailsjacket/" + jacketId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/jacketremovecategory")
    public String jacketremovecategory(@RequestParam(name = "category_id") Long categoryId,
                                       @RequestParam(name = "jacket_id") Long jacketId){
        Jackets jackets = userServices.getJacket(jacketId);
        if (jackets != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = jackets.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (categories.contains(category)) {
                    categories.remove(category);
                    jackets.setCategories(categories);
                    userServices.saveJacket(jackets);
                }
                return "redirect:/admin/detailsjacket/" + jacketId;
            }
        }
        return "redirect:/";
    }

//Short
    @PostMapping(value = "/shortassigncategory")
    public String shortassigncategory(@RequestParam(name = "category_id") Long categoryId,
                                      @RequestParam(name = "shorts_id") Long shortId){
        Shorts shorts = userServices.getShorts(shortId);
        if (shorts != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = shorts.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (!categories.contains(category)) {
                    categories.add(category);
                    shorts.setCategories(categories);
                    userServices.saveShort(shorts);
                }

                return "redirect:/admin/detailsshort/" + shortId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/shortremovecategory")
    public String shortremovecategory(@RequestParam(name = "category_id") Long categoryId,
                                      @RequestParam(name = "shorts_id") Long shortId){
        Shorts shorts = userServices.getShorts(shortId);
        if (shorts != null) {
            Categories category = userServices.getCategories(categoryId);
            if (category != null) {
                List<Categories> categories = shorts.getCategories();
                if (categories==null) {
                    categories = new ArrayList<>();
                }
                if (categories.contains(category)) {
                    categories.remove(category);
                    shorts.setCategories(categories);
                    userServices.saveShort(shorts);
                }
                return "redirect:/admin/detailsshort/" + shortId;
            }
        }
        return "redirect:/";
    }






//Add-City
    @GetMapping(value = "/addcity")
    public String addcity(Model model) {

        List<Cities> city = userServices.getAllCity();
        model.addAttribute("city", city);

        model.addAttribute("CURRENT_USER", getUser());
        return "addcity";
    }

    @PostMapping(value = "/addcity")
    public String addCity(@RequestParam(name = "city_name") String cityName) {

        Cities cities = userServices.checkCity(cityName);

        if (cities == null) {

            Cities newCities = new Cities();
            newCities.setName(cityName);

            userServices.addCity(newCities);

            return "redirect:/admin/addcity";
        }
        return "redirect:/admin/addcity?error";
    }

    @GetMapping(value = "/detailscity/{cityId}")
    public String cityDetails(Model model, @PathVariable(name = "cityId") Long id) {
        Cities city = userServices.getCity(id);
        model.addAttribute("city", city);

        return "detailscity";
    }



    @PostMapping(value = "/savecity")
    public String saveCity(@RequestParam(name = "id") Long id,
                           @RequestParam(name = "city_name") String cityName) {

        Cities cities = userServices.getCity(id);

        if (cities != null) {
            cities.setName(cityName);

            userServices.saveCity(cities);

            return "redirect:/admin/addcity/";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deletecity")
    public String deleteCity(@RequestParam(name = "id") Long id) {

        Cities city = userServices.getCity(id);
        if (city != null) {
            userServices.deleteCity(city);
        }
        return "redirect:/";
    }


    @GetMapping(value = "/viewave/{avaURL}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewAva(@PathVariable(name = "avaURL") String avaURL) throws IOException {

        String picURL = loadURL + "default.png";

        if (avaURL != null) {
            picURL = loadURL + avaURL;
        }

        InputStream in;

        try {
            ClassPathResource  classPathResource = new ClassPathResource(picURL);
            in = classPathResource.getInputStream();
        } catch (Exception e) {
            picURL = loadURL + "default.png";
            ClassPathResource  classPathResource = new ClassPathResource(picURL);
            in = classPathResource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }


    @PostMapping(value = "/uploadava")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(@RequestParam(name = "user_ava") MultipartFile file) {
        try {
            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
                String fileName = DigestUtils.sha1Hex("avatar_"+getUser().getId())+".jpg";
                byte bytes [] = file.getBytes();
                Path path = Paths.get(targetURL+fileName);
                Files.write(path, bytes);


                Users currentUser = getUser();
                currentUser.setAvatar(fileName);
                userServices.saveUser(currentUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }





    @PostMapping(value = "/uploadimg")
    @PreAuthorize("isAuthenticated()")
    public String uploadtshirtsImage(
//                                    @RequestParam(name = "tshirt_card_img") Long id,
//                                    @RequestParam(name = "shirt_card_img") Long shirtCardimg,
//                                    @RequestParam(name = "newitem_card_img") Long newItemCardimg,
//                                    @RequestParam(name = "sweetshirt_card_img") Long sweetshirtCardimg,
//                                    @RequestParam(name = "jacket_card_img") Long jacketCardImg,
                                      @RequestParam(name = "short_card_img") Long shortCardimg,
                                      @RequestParam(name = "card_img") MultipartFile file) {
        try {
            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
                String fileName = DigestUtils.sha1Hex("img_"+ shortCardimg)+".jpg";
                byte bytes [] = file.getBytes();
                Path path = Paths.get(imageTargetUrl+fileName);
                Files.write(path, bytes);

//                Tshirts currentTshirts = userServices.getTshirts(id);
//                currentTshirts.setCardimg(fileName);
//                userServices.saveTshirts(currentTshirts);

//                Shirts currentShirts = userServices.getShirts(shirtId);
//                currentShirts.setShirtcardimg(fileName);
//                userServices.saveShirts(currentShirts);

//                NewItems currentNewItem = userServices.getNewItem(newItemId);
//                currentNewItem.setNewitemcardimg(fileName);
//                userServices.saveNewItem(currentNewItem);

//                Sweetshirts currentSweetshirts = userServices.getSweetshirt(sweetshirtCardimg);
//                currentSweetshirts.setSweetshirtcardimg(fileName);
//                userServices.saveSweetshirt(currentSweetshirts);
//

//                  Jackets currentJacket = userServices.getJacket(jacketCardImg);
//                  currentJacket.setJacketcardimg(fileName);
//                  userServices.saveJacket(currentJacket);

                Shorts currentShorts = userServices.getShorts(shortCardimg);
                currentShorts.setShortcardimg(fileName);
                userServices.saveShort(currentShorts);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/detailsshort";
    }






    @GetMapping(value = "/profileadmin")
    public String profileAdmin(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "profileadmin";
    }

    @PostMapping(value = "/updatefullname")
    @PreAuthorize("isAuthenticated()")
    public String updateFullName(@RequestParam(name = "new_name") String newName) {
        Users currentUser = getUser();

        if (currentUser!=null) {
            currentUser.setFullName(newName);
            userServices.saveUser(currentUser);
            return "redirect:/admin/profileadmin?success";
        }
        return "redirect:/admin/profileadmin?error";

    }

    @PostMapping(value = "/updatepassword")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(@RequestParam(name = "old_password") String oldPassword,
                                 @RequestParam(name = "new_password") String newPassword,
                                 @RequestParam(name = "re_new_password") String reNewPassword) {
        Users currentUser = getUser();

        if (newPassword.trim().equals(reNewPassword.trim())) {
            if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {

                currentUser.setPassword(passwordEncoder.encode(newPassword));
                userServices.saveUser(currentUser);

                return "redirect:/admin/profileadmin?success";
            }
        }
        return "redirect:/admin/profileadmin?error";
    }

    private Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users)authentication.getPrincipal();
            return user;
        }
        return null;
    }





}
