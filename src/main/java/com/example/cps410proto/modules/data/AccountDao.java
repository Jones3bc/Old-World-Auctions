package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.PaymentMethod;
import org.thymeleaf.util.StringUtils;

/**
 * Interacts with the database to retrieve and store account information.
 * Also validates {@link PaymentMethod}s.
 */
public class AccountDao {
    /**
     * Checks the validity of a given {@link PaymentMethod}.
     * A payment method is valid if its ID > 0, card number is formatted correctly (xxxx-xxxx-xxxx-xxxx),
     * expiration month/year are valid months/years, and the cvv is exactly 3 or 4 digits.
     *
     * @param paymentMethod The {@link PaymentMethod} to check for validity.
     * @return True if the method is valid. Should throw an {@link IllegalArgumentException} otherwise.
     * @throws IllegalArgumentException if the method is invalid.
     */
    public boolean isMethodValid(PaymentMethod paymentMethod) throws IllegalArgumentException {
        int cvvLength = String.valueOf(paymentMethod.getCvv()).length();
        int expirationMonth = paymentMethod.getExpirationMonth();
        int expirationYear = paymentMethod.getExpirationYear();

        if (StringUtils.isEmptyOrWhitespace(paymentMethod.getCardNumber())) {
            throw new IllegalArgumentException("Card number field for a payment method cannot be null or contain only whitespace.");
        } else if (paymentMethod.getId() <= 0) {
            throw new IllegalArgumentException("ID field for a payment method must be > 0");
        } else if (!(paymentMethod.getCardNumber().matches("^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$"))) {
            throw new IllegalArgumentException("Card number field for a payment method must be in the format"
                    + "'xxxx-xxxx-xxxx-xxxx'.");
        } else if (expirationMonth <= 0 || expirationMonth > 12) {
            throw new IllegalArgumentException("Expiration month must be a number between 1-12.");
        } else if (expirationYear < 0 || expirationYear > 99) {
            throw new IllegalArgumentException("Expiration year must be a number between 0-99");
        } else if (paymentMethod.getCvv() <= 0 || cvvLength > 4 || cvvLength < 3) {
            throw new IllegalArgumentException("CVV number must contain 3 or 4 digits.");
        }

        return true;
    }
}
