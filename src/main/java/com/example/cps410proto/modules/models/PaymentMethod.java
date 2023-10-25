package com.example.cps410proto.modules.models;

/**
 * Represents a payment method.
 *
 * @author Brock Jones
 */
public class PaymentMethod {
    private final int id;
    private final boolean credit;
    private final String cardNumber;
    private final int expirationMonth;
    private final int expirationYear;
    private final int cvv;

    public PaymentMethod(int id, boolean credit, String cardNumber, int expirationMonth, int expirationYear, int cvv) {
        this.id = id;
        this.credit = credit;
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
    }

    public int getId() {
        return id;
    }

    public boolean isCredit() {
        return credit;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public int getCvv() {
        return cvv;
    }
}
