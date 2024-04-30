package edu.cmich.oldworldauction.modules.models;

/**
 * Represents a payment method.
 */
public class PaymentMethod {
    private String paymentId;
    private final boolean credit;
    private final String cardNumber;
    private final int expirationMonth;
    private final int expirationYear;
    private final int cvv;
    private final String userId;

    /**
     * Primary constructor for this class.
     *
     * @param id The ID of the payment method
     * @param credit If true, this is a credit card. If false, debit
     * @param cardNumber The number associated with the card/payment method
     * @param expirationMonth The expiration month of the card/payment method
     * @param expirationYear The expiration year of the card/payment method
     * @param cvv The CVV on the back of the card/payment method
     * @param userID The ID of the user that this payment method is associated with
     */
    public PaymentMethod(String id, boolean credit, String cardNumber, int expirationMonth, int expirationYear, int cvv, String userID) {
        this.paymentId = id;
        this.credit = credit;
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
        this.userId = userID;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

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

    public String getUserId() { return userId; }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentId='" + paymentId + '\'' +
                ", credit=" + credit +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationMonth=" + expirationMonth +
                ", expirationYear=" + expirationYear +
                ", cvv=" + cvv +
                ", userId='" + userId + '\'' +
                '}';
    }
}
