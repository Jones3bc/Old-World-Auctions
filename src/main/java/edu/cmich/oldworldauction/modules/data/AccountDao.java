package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.PaymentMethod;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Interacts with the database to retrieve, update, and store {@link PaymentMethod}s.
 * Also validates {@link PaymentMethod}s.
 */
@Service
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
        } else if (StringUtils.isEmptyOrWhitespace(paymentMethod.getPaymentId())) {
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
     * Retrieves all payment methods for a given user ID.
     *
     * @param userID - The user's ID.
     * @return A {@link List} of {@link PaymentMethod}s that are associated with the user.
     */
    public List<PaymentMethod> retrieveAllPaymentMethodsForUser (String userID){
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * from PAYMENT_METHODS WHERE userId = ?;";
        List<PaymentMethod> paymentMethods = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                System.out.println("Payment method");
                PaymentMethod paymentMethod =
                        new PaymentMethod(
                                resultSet.getString("paymentID"),
                                resultSet.getBoolean("credit"),
                                resultSet.getString("cardNumber"),
                                resultSet.getInt("expirationMonth"),
                                resultSet.getInt("expirationYear"),
                                resultSet.getInt("cvv"),
                                resultSet.getString("userID")
                        );
                System.out.println(paymentMethod);
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
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * from PAYMENT_METHODS where paymentID = ?;";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new PaymentMethod(
                                resultSet.getString("paymentID"),
                                resultSet.getBoolean("credit"),
                                resultSet.getString("cardNumber"),
                                resultSet.getInt("expirationMonth"),
                                resultSet.getInt("expirationYear"),
                                resultSet.getInt("cvv"),
                                resultSet.getString("userID")
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
    public void insertPaymentMethod (PaymentMethod paymentMethod)  {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "INSERT INTO PAYMENT_METHODS VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setBoolean(2, paymentMethod.isCredit());
            preparedStatement.setString(3, paymentMethod.getCardNumber());
            preparedStatement.setInt(4, paymentMethod.getExpirationMonth());
            preparedStatement.setInt(5, paymentMethod.getExpirationYear());
            preparedStatement.setInt(6, paymentMethod.getCvv());
            preparedStatement.setString(7, paymentMethod.getUserId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    /**
     * Updates a given {@link PaymentMethod} in the database.
     *
     * @param paymentMethod The updated payment method.
     */
    public void updatePaymentMethod(PaymentMethod paymentMethod) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
            UPDATE PAYMENT_METHODS
            SET credit = ?,
                cardNumber = ?,
                expirationMonth = ?,
                expirationYear = ?,
                cvv = ?
            WHERE paymentID = ?
            AND   userID = ?;
        """;

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, paymentMethod.isCredit());
            preparedStatement.setString(2, paymentMethod.getCardNumber());
            preparedStatement.setInt(3, paymentMethod.getExpirationMonth());
            preparedStatement.setInt(4, paymentMethod.getExpirationYear());
            preparedStatement.setInt(5, paymentMethod.getCvv());
            preparedStatement.setString(6, paymentMethod.getPaymentId());
            preparedStatement.setString(7, paymentMethod.getUserId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    /**
     * Checks if a card number is present in the database for a given user.
     *
     * @param cardNumber The card number to check for
     * @param userId The user to check for
     * @return true if the card is present for the user, false otherwise
     */
    public boolean isCardNumberPresentForUser(String cardNumber, String userId) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
            SELECT
                COUNT(*) AS METHOD_COUNT
            FROM
                PAYMENT_METHODS
            WHERE
                cardNumber = ?
            AND userId = ?;
        """;

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.getInt("METHOD_COUNT") > 0;
        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
        return true;
    }

    public void deletePaymentMethod(String paymentMethodId) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
            DELETE FROM PAYMENT_METHODS
            WHERE paymentID = ?
        """;

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, paymentMethodId);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }
}
