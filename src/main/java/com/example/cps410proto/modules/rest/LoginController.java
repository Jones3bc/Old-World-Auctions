package com.example.cps410proto.modules.rest;

import com.example.cps410proto.modules.data.LoginDao;
import com.example.cps410proto.modules.models.AuctionItem;
import com.example.cps410proto.modules.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles requests and responses having to do with logging in.
 * Supplies appropriate pages to the user and takes user input.
 */
@RequestMapping("/")
@Controller
public class LoginController {

    @Autowired
    LoginDao loginDao;

    /**
     * Supplies the home/index page to the user in the browser.
     *
     * @return The home/index HTML page.
     */
    public String index(){
        return "index";
    }

    /**
     * Supplies the login page to the user in the browser.
     * @return The login HTML page.
     */
    @GetMapping("/login-page")
    public String logInPage(){
        return "logIn";
    }

    /**
     * Supplies the register page to the user in the browser.
     * @return The register HTML page.
     */
    @GetMapping("/register-page")
    public String registrationPage(){
        return "registration";
    }

    /**
     * Submits login request. Should send user to home page on login success. Display error to the user otherwise.
     * @param user An {@link User} that holds the user's login information.
     * @return The home page on success. Error otherwise.
     */
    @PostMapping("/login")
    public String logIn(@ModelAttribute User user, Model model){
        List<User> currentUsers = loginDao.retrieveUsers();
        System.out.println(currentUsers);

        for(User retrievedUser : currentUsers) {
            if(retrievedUser.getUsername().equals(user.getUsername()) && retrievedUser.getPassword().equals(user.getPassword())) {
                Map<String, String> attributes = new HashMap<>();
                attributes.put("username", user.getUsername());
                attributes.put("password", user.getPassword());
                attributes.put("welcome", "Welcome, " + user.getUsername());
                model.addAllAttributes(attributes);
            }
        }

        return "index";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute User user){
        loginDao.insertUser(user);

        return "registrationConfirmation";
    }
}
