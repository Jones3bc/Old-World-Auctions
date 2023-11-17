package com.example.cps410proto.modules.rest;

import com.example.cps410proto.modules.data.LoginDao;
import com.example.cps410proto.modules.models.AuctionItem;
import com.example.cps410proto.modules.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests and responses having to do with logging in.
 * Supplies appropriate pages to the user and takes user input.
 */
@RequestMapping("/")
@Controller
public class LoginController {
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
     * Submits login request. Should send user to home page on login success. Display error to the user otherwise.
     * @param user An {@link User} that holds the user's login information.
     * @return The home page on success. Error otherwise.
     */
    @PostMapping("/login")
    public void logIn(@ModelAttribute User user){
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
    }

    @GetMapping("/test")
    public String query(){
        LoginDao loginDao = new LoginDao();
        System.out.println(loginDao.retrieveUser());
        return "confirmation";
    }
}
