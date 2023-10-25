package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.PaymentMethod;

/**
 * Interacts with the database to retrieve and store account information.
 *
 * @author Brock Jones
 */
public class AccountDao {
    /**
     * Checks the validity of a given {@link PaymentMethod}.
     *
     * @throws IllegalArgumentException if the method is invalid.
     * @param paymentMethod The {@link PaymentMethod} to check for validity.
     * @return True if the method is valid. Should throw an {@link IllegalArgumentException} otherwise.
     */
    public boolean isMethodValid(PaymentMethod paymentMethod) throws IllegalArgumentException{
        return true;
    }
}
