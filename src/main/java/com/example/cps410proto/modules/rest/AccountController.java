package com.example.cps410proto.modules.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.cps410proto.modules.models.User;
import com.example.cps410proto.modules.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * Handles all requests and responses having to do with accounts/account information.
 *
 * @author Brock Jones
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

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

    @PutMapping("/changePassword/{username}")
    public User changePassword(@PathVariable String username, @RequestParam String newPassword) {
        try {
            return accountService.changePassword(username, newPassword);
        } catch (Exception e) {
            // Handle the exception, e.g., return an error response
            return null;
        }
    }

    @DeleteMapping("/deleteAccount/{username}")
    public void deleteAccount(@PathVariable String username) {
        accountService.deleteAccount(username);

    }
}
