package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.LoginDao;
import edu.cmich.oldworldauction.modules.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * Handles requests and responses having to do with logging in and our home page.
 * Supplies appropriate pages to the user and takes user input.
 */
@RequestMapping("/")
@Controller
public class LoginController {

    public String loggedInUser = "";

    public String loggedInUserID = "";

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

        for (User retrievedUser : currentUsers) {
            System.out.println(retrievedUser);
            if (retrievedUser.getUsername().equals(user.getUsername()) && retrievedUser.getPassword().equals(user.getPassword())) {
                this.loggedInUser = user.getUsername();
                this.loggedInUserID = retrievedUser.getUserID();
                break;
            }
        }

        return "index";
    }

    private record CheckPasswordResponse(String isValid){}
    @GetMapping("/check-password")
    @ResponseBody
    public CheckPasswordResponse checkPassword(@RequestParam String password) {
        List<User> currentUsers = loginDao.retrieveUsers();

        for (User retrievedUser : currentUsers) {
            if (retrievedUser.getUsername().equals(this.loggedInUser) && retrievedUser.getPassword().equals(password)) {
                return new CheckPasswordResponse("true");
            }
        }

        return new CheckPasswordResponse("false");
    }

    /**
     * Supplies the contact page to the user in the browser.
     *
     * @return The contact HTML page.
     */
    @GetMapping("/contact")
    public String contactPage(){
        return "contact";
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

    /**
     * Represents the request given from the user update form from the account.html page.
     *
     * @param username {@link String} The updated username of the user. Or Blank if the username is not being updated.
     * @param password {@link String} The updated password of the user. Or Blank if the password is not being updated.
     * @param originalPassword {@link String} The original password of the user. Used for validation before updating.
     */
    private record UserUpdateRequest(String username, String password, String originalPassword){}

    /**
     * Updates a user given updated user information and the original password.
     * @param userUpdateRequest {@link UserUpdateRequest} That represents updated user information and the original password.
     * @return The home page of the website (update this to go to a confirmation page).
     */
    @PostMapping("/update-user-info")
    public String updateUserInfo(@ModelAttribute UserUpdateRequest userUpdateRequest) {
        List<User> currentUsers = loginDao.retrieveUsers();
        for (User retrievedUser : currentUsers) {
            if (retrievedUser.getUsername().equals(this.loggedInUser) && retrievedUser.getPassword().equals(userUpdateRequest.originalPassword)) {
                String updatedUsername;
                String updatedPassword;
                if(StringUtils.isEmptyOrWhitespace(userUpdateRequest.username)) {
                    updatedUsername = retrievedUser.getUsername();
                } else {
                    updatedUsername = userUpdateRequest.username;
                }

                if(StringUtils.isEmptyOrWhitespace(userUpdateRequest.password)) {
                    updatedPassword = retrievedUser.getPassword();
                } else {
                    updatedPassword = userUpdateRequest.password;
                }
                User updatedUser = new User(retrievedUser.getUserID(), updatedUsername, updatedPassword);

                this.loginDao.updateUser(updatedUser);

                this.loggedInUser = "";
                this.loggedInUserID = "";
            }
        }

        return "index"; //Create a confirmation page and replace index here.
    }
}
