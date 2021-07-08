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
public class MainController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${loadUrl}")
    private String loadURL;

    @Value("${targetUrl}")
    private String targetURL;


    @Value("${imageLoadUrl}")
    private String imageLoadUrl;

    @GetMapping(value = "/")
    public String index(Model model){

        List<Cities> city = userServices.getAllCity();
        model.addAttribute("city", city);

        List<Tshirts> tshirts = userServices.getAllTshirts();
        model.addAttribute("tshirts", tshirts);

        List<Shirts> shirts = userServices.getAllShirts();
        model.addAttribute("shirts", shirts);

        List<NewItems> newitems = userServices.getAllNewItem();
        model.addAttribute("newitem", newitems);

        List<Sweetshirts> sweetshirts = userServices.getAllSweetshirt();
        model.addAttribute("sweetshirts", sweetshirts);

        List<Jackets> jackets = userServices.getAllJackets();
        model.addAttribute("jackets", jackets);


        return "index";
    }

    @GetMapping(value = "/newitem")
    public String newitem(Model model) {

        List<NewItems> newitems = userServices.getAllNewItem();
        model.addAttribute("newitem", newitems);

        return "newitem";
    }

    @GetMapping(value = "/tshirt")
    public String tshirt(Model model) {

        List<Tshirts> tshirts = userServices.getAllTshirts();
        model.addAttribute("tshirts", tshirts);

        return "tshirt";
    }

    @GetMapping(value = "/shirts")
    public String shirts(Model model) {

        List<Shirts> shirts = userServices.getAllShirts();
        model.addAttribute("shirts", shirts);

        return "shirts";
    }

    @GetMapping(value = "/sweatshirts")
    public String sweatshirts(Model model) {

        List<Sweetshirts> sweetshirts = userServices.getAllSweetshirt();
        model.addAttribute("sweetshirt", sweetshirts);

        return "sweetshirts";
    }

    @GetMapping(value = "/jackets")
    public String jackets(Model model) {

        List<Jackets> jackets = userServices.getAllJackets();
        model.addAttribute("jackets", jackets);

        return "jackets";
    }

    @GetMapping(value = "/shorts")
    public String shorts(Model model) {

        List<Shorts> shorts = userServices.getAllShorts();
        model.addAttribute("shorts", shorts);


        return "shorts";
    }

    @GetMapping(value = "/pants")
    public String pants(Model model) {
      ;
        return "pants";
    }

    @GetMapping(value = "/accessories")
    public String accessories(Model model) {

        return "accessories";
    }

    @GetMapping(value = "/favorites")
    public String favorites(Model model) {

        return "favorites";
    }


    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "profile";
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

                return "redirect:/profile?success";
            }
        }
        return "redirect:/profile?error";
    }



    @PostMapping(value = "/updatefullname")
    @PreAuthorize("isAuthenticated()")
    public String updateFullName(@RequestParam(name = "new_name") String newName) {
        Users currentUser = getUser();

        if (currentUser!=null) {
            currentUser.setFullName(newName);
            userServices.saveUser(currentUser);
            return "redirect:/profile?success";
        }
        return "redirect:/profile?error";

    }

    @GetMapping(value = "/moderator")
    @PreAuthorize("hasRole('ROLE_REDACTOR')")
    public String moderator(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "moderator";
    }

    @GetMapping(value = "/accessdenied")
    public String accessdenied(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "accessdenied";
    }

    @GetMapping(value = "/registerpage")
    public String registerPage(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam(name = "user_email") String userEmail,
                           @RequestParam(name = "user_password") String userPassword,
                           @RequestParam(name = "re_user_password") String userRePassword,
                           @RequestParam(name = "user_full_name") String fullName) {

        Users user = userServices.checkUser(userEmail);

        if (user == null && userPassword.trim().equals(userRePassword.trim())) {

            Roles userRole = userServices.findRoleByName("ROLE_USER");
            List<Roles> roles = new ArrayList<>();
            roles.add(userRole);

            Users newUser = new Users();
            newUser.setEmail(userEmail);
            newUser.setRoles(roles);
            newUser.setFullName(fullName);
            newUser.setPassword(passwordEncoder.encode(userPassword));

            userServices.addUser(newUser);

            return "redirect:/";

        }
        return "redirect:/registerpage?error";
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
        return "redirect:/profileadmin";
    }


    @GetMapping(value = "/viewave/{avaURL}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    byte[] viewAva(@PathVariable(name = "avaURL") String avaURL) throws IOException {

        String picURL = loadURL + "default.png";

        if (avaURL != null) {
            picURL = loadURL + avaURL;
        }

        InputStream in;

        try {
            ClassPathResource classPathResource = new ClassPathResource(picURL);
            in = classPathResource.getInputStream();
        } catch (Exception e) {
            picURL = loadURL + "default.png";
            ClassPathResource  classPathResource = new ClassPathResource(picURL);
            in = classPathResource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }


    @GetMapping(value = "/image/{imgURL}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] viewtshirtImg(@PathVariable(name = "imgURL") String imgURL) throws IOException {

        String picImgURL = imageLoadUrl + "";

        if (imgURL != null) {
            picImgURL = imageLoadUrl + imgURL;
        }
        InputStream in;
        try {
            ClassPathResource  classPathResource = new ClassPathResource(picImgURL);
            in = classPathResource.getInputStream();
        } catch (Exception e) {
            picImgURL = imageLoadUrl + "";
            ClassPathResource  classPathResource = new ClassPathResource(picImgURL);
            in = classPathResource.getInputStream();
        }
        return IOUtils.toByteArray(in);
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
