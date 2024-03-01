package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.LoginDao;
import edu.cmich.oldworldauction.modules.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles requests and responses having to do with logging in and our home page.
 * Supplies appropriate pages to the user and takes user input.
 */
@RequestMapping("/")
@Controller
public class LoginController {

    private String loggedInUser = "";

    private String loggedInUserID = "";

    @Autowired
    LoginDao loginDao;

    /**
     * Supplies the home/index page to the user in the browser.
     *
     * @return The home/index HTML page.
     */
    public String index() {
        return "index";
    }

    private record Username(String username) {}

    private record UserId(String userId) {}

    @ResponseBody
    @GetMapping("/loggedUser")
    public Username getLoggedUser() {
        return new Username(this.loggedInUser);
    }

    @ResponseBody
    @GetMapping("/loggedUserID")
    public UserId getLoggedUserID() { return new UserId(this.loggedInUserID); }

    /**
     * Supplies the login page to the user in the browser.
     *
     * @return The login HTML page.
     */
    @GetMapping("/login-page")
    public String logInPage() {
        return "logIn";
    }

    /**
     * Supplies the register page to the user in the browser.
     *
     * @return The register HTML page.
     */
    @GetMapping("/register-page")
    public String registrationPage() {
        return "registration";
    }

    /**
     * Submits login request. Should send user to home page on login success. Display error to the user otherwise.
     *
     * @param user An {@link User} that holds the user's login information.
     * @return The home page on success. Error otherwise.
     */
    @PostMapping("/login")
    public String logIn(@ModelAttribute User user, Model model) {
        List<User> currentUsers = loginDao.retrieveUsers();
        System.out.println(currentUsers);

        for (User retrievedUser : currentUsers) {
            if (retrievedUser.getUsername().equals(user.getUsername()) && retrievedUser.getPassword().equals(user.getPassword())) {
                this.loggedInUser = user.getUsername();
                this.loggedInUserID = user.getUserID();
                break;
            }
        }

        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        this.loggedInUser = "";
        this.loggedInUserID = "";

        return "index";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute User user) {
        loginDao.insertUser(user);

        return "registrationConfirmation";
    }
}
