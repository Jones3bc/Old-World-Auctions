package edu.cmich.oldworldauction.modules.rest;

import edu.cmich.oldworldauction.modules.data.AccountDao;
import edu.cmich.oldworldauction.modules.models.PaymentMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Handles all requests and responses having to do with {@link PaymentMethod}s and the user account page.
 */
@RequestMapping("/")
@Controller
public class AccountController {

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

    /**
     * Represents the request for creating a payment method.
     */
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

    /**
     * Represents the request needed to update a payment method.
     */
    private record UpdatePaymentMethodRequest(
            String cardNumber,
            int expirationMonth,
            int expirationYear,
            int cvv,
            String credit,
            String userId,
            String paymentMethodId
    ) {
    }

    /**
     * Updates a given {@link PaymentMethod} for a user.
     *
     * @param request The updated {@link PaymentMethod} information
     * @return The account HTML page
     */
    @PostMapping("/update-payment-method")
    public String updatePaymentMethod(@ModelAttribute UpdatePaymentMethodRequest request) {
        boolean credit = request.credit.equals("Yes");
        System.out.println(request);
        PaymentMethod updatedPaymentMethod = new PaymentMethod(
                request.paymentMethodId(),
                credit,
                request.cardNumber(),
                request.expirationMonth(),
                request.expirationYear(),
                request.cvv(),
                request.userId()
        );
        System.out.println(updatedPaymentMethod);

        this.accountDao.updatePaymentMethod(updatedPaymentMethod);

        return "account";
    }

    /**
     * Models the response given to a request for checking a card number.
     */
    private record CheckCardNumberResponse(boolean isValid) {}

    /**
     * Checks to see if a card number already exists for a user. The user should not be able to use this number if so.
     *
     * @param cardNumber The card number to check for
     * @param userId The user ID to check the card number for
     * @return {@link CheckCardNumberResponse} that holds true if the card is not present and false otherwise
     */
    @GetMapping("/check-card-number")
    @ResponseBody
    public CheckCardNumberResponse checkCardNumber(@RequestParam String cardNumber, @RequestParam String userId){
        boolean isPresent = this.accountDao.isCardNumberPresentForUser(cardNumber, userId);
        return new CheckCardNumberResponse(!isPresent);
    }

    /**
     * Allows users to delete {@link PaymentMethod}s.
     *
     * @param paymentMethodId The ID of the {@link PaymentMethod} to delete
     * @return the account page to the user
     */
    @PostMapping("/delete-payment-method")
    public String deletePaymentMethod(@RequestParam String paymentMethodId) {
        this.accountDao.deletePaymentMethod(paymentMethodId);
        return "account";
    }

}
