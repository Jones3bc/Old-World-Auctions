package com.example.cps410proto.modules.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cps410proto.modules.models.User;
import com.example.cps410proto.modules.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Handles all requests and responses having to do with accounts/account information.
 */
@RequestMapping("/")
@Controller
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/create")
    public String auctionPage(){
        return "Account";
    }

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public User createAccount(@RequestParam String username, @RequestParam String password) {
        try {
            return accountService.createAccount(username, password);
        } catch (Exception e) {

            return null;
        }
    }

    @PostMapping("/change")
    public User changePassword(@PathVariable String username, @RequestParam String newPassword) {
        try {
            return accountService.changePassword(username, newPassword);
        } catch (Exception e) {
            // Handle the exception, e.g., return an error response
            return null;
        }
    }

    @PostMapping("/delete")
    public void deleteAccount(@PathVariable String username) {
        accountService.deleteAccount(username);

    }
}
