package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.cmich.oldworldauction.modules.models.User;
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

}
