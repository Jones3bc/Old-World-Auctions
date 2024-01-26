package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.AccountDao;
import edu.cmich.oldworldauction.modules.models.PaymentMethod;
import edu.cmich.oldworldauction.modules.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.cmich.oldworldauction.modules.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Handles all requests and responses having to do with payment method information.
 */
@RequestMapping("/")
@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountDao accountDao;

    /**
     * Creates a new payment method.
     *
     * @param paymentMethod The {@link PaymentMethod} to create.
     */
    @PostMapping("/create-payment-method")
    public void createPaymentMethod(@RequestBody PaymentMethod paymentMethod){
        this.accountDao.insertPaymentMethod(paymentMethod);
    }

    /**
     * Retrieves a {@link PaymentMethod} given its ID.
     *
     * @param id The ID of the {@link PaymentMethod}
     * @return The retrieved {@link PaymentMethod}.
     */
    @GetMapping("/retrieve-payment-method/{id}")
    public PaymentMethod retrievePaymentMethodById(@PathVariable int id) {
        return this.accountDao.retrievePaymentMethod(id);
    }

    /**
     * Retrieves all {@link PaymentMethod}s that belong to a specified user.
     *
     * @param username The username of the user.
     * @return A {@link List} of the retrieved {@link PaymentMethod}s.
     */
    @GetMapping("/retrieve-payment-methods/{username}")
    public List<PaymentMethod> retrieveUserPaymentMethods(@PathVariable String username){
        return this.accountDao.retrieveAllPaymentMethodsForUser(username);
    }

    @GetMapping("/create")
    public String auctionPage(){
        return "Account";
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
