package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.PaymentMethod;
import org.thymeleaf.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Retrieves all payment methods for a given user.
     *
     * @param usersUsername - The user's username.
     * @return A {@link List} of {@link PaymentMethod}s that are associated with the user.
     */
    public List<PaymentMethod> retrieveAllPaymentMethodsForUser (String usersUsername){
        String sqlConnection = "jdbc:sqlite:/F:\\SqlLite\\usersdb.db";
        String sql = "SELECT * from paymentMethods where usersUsername = ?;";
        List<PaymentMethod> paymentMethods = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usersUsername);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                PaymentMethod paymentMethod =
                        new PaymentMethod(
                                resultSet.getInt("id"),
                                resultSet.getBoolean("credit"),
                                resultSet.getString("cardNumber"),
                                resultSet.getInt("expirationMonth"),
                                resultSet.getInt("expirationYear"),
                                resultSet.getInt("cvv"),
                                resultSet.getString("usersUsername")
                        );

                paymentMethods.add(paymentMethod);
            }
        } catch (SQLException ex){
            System.out.println("Failed to establish and use SQL connection.");
        }

        return paymentMethods;
    }

    /**
     * Retrieves a {@link PaymentMethod} given its ID.
     *
     * @param id The ID of the {@link PaymentMethod}.
     * @return The retrieved {@link PaymentMethod}.
     */
    public PaymentMethod retrievePaymentMethod(int id){
        String sqlConnection = "jdbc:sqlite:/F:\\SqlLite\\usersdb.db";
        String sql = "SELECT * from paymentMethods where id = ?;";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new PaymentMethod(
                                resultSet.getInt("id"),
                                resultSet.getBoolean("credit"),
                                resultSet.getString("cardNumber"),
                                resultSet.getInt("expirationMonth"),
                                resultSet.getInt("expirationYear"),
                                resultSet.getInt("cvv"),
                                resultSet.getString("usersUsername")
                        );
            }
        } catch (SQLException ex){
            System.out.println("Failed to establish and use SQL connection.");
        }

        return null;
    }

    /**
     * Inserts a {@link PaymentMethod} into the payment method database table.
     *
     * @param paymentMethod The {@link PaymentMethod} to add to the table.
     */
    public void insertPaymentMethod (PaymentMethod paymentMethod){
        String sqlConnection = "jdbc:sqlite:/F:\\SqlLite\\usersdb.db";
        String sql = "INSERT INTO paymentMethods VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, paymentMethod.getId());
            preparedStatement.setBoolean(2, paymentMethod.isCredit());
            preparedStatement.setString(3, paymentMethod.getCardNumber());
            preparedStatement.setInt(4, paymentMethod.getExpirationMonth());
            preparedStatement.setInt(5, paymentMethod.getExpirationYear());
            preparedStatement.setInt(6, paymentMethod.getCvv());
            preparedStatement.setString(7, paymentMethod.getUserUsername());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection.");
        }
    }
}
