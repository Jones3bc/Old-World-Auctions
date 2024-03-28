package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.AccountDao;
import edu.cmich.oldworldauction.modules.models.PaymentMethod;
import edu.cmich.oldworldauction.modules.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

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
     * Reroutes the user to the account management page.
     *
     * @return The account management page.
     */
    @GetMapping("/account-page")
    public String getAccountPage() {
        return "account";
    }

    private record PaymentMethodCreateRequest(
            String cardNumber,
            int expirationMonth,
            int expirationYear,
            int cvv,
            String credit,
            String userId
    ) {
    }

    /**
     * Creates a new payment method.
     *
     * @param request The {@link PaymentMethodCreateRequest} that holds {@link PaymentMethod} information.
     */
    @PostMapping("/create-payment-method")
    public String createPaymentMethod(@ModelAttribute PaymentMethodCreateRequest request) {
        boolean credit = request.credit.equals("Yes");
        PaymentMethod paymentMethod = new PaymentMethod(
                null,
                credit,
                request.cardNumber(),
                request.expirationMonth(),
                request.expirationYear(),
                request.cvv(),
                request.userId()
        );
        this.accountDao.insertPaymentMethod(paymentMethod);
        return "account";
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
     * @param userId The user ID of the user.
     * @return A {@link List} of the retrieved {@link PaymentMethod}s.
     */
    @GetMapping("/retrieve-payment-methods")
    @ResponseBody
    public List<PaymentMethod> retrieveUserPaymentMethods(@RequestParam String userId) {
        return this.accountDao.retrieveAllPaymentMethodsForUser(userId);
    }

}
