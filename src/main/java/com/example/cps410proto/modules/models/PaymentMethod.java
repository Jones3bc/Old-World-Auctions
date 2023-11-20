package com.example.cps410proto.modules.models;

/**
 * Represents a payment method.
 */
public class PaymentMethod {
    private final int id;
    private final boolean credit;
    private final String cardNumber;
    private final int expirationMonth;
    private final int expirationYear;
    private final int cvv;
    private final String userUsername;

    public PaymentMethod(int id, boolean credit, String cardNumber, int expirationMonth, int expirationYear, int cvv, String userUsername) {
        this.id = id;
        this.credit = credit;
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
        this.userUsername = userUsername;
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

    public String getUserUsername() { return userUsername; }
}
