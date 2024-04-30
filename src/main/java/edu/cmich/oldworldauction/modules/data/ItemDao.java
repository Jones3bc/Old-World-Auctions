package edu.cmich.oldworldauction.modules.data;
import edu.cmich.oldworldauction.modules.models.AuctionItemRetrieve;
import edu.cmich.oldworldauction.modules.models.AuctionItemInsert;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Interacts with the database to retrieve, store, delete, and update auction items.
 */
@Service
public class ItemDao {
    /**
     * Takes a SQL formatted date and converts it into a Java formatted date.
     * All in String representation.
     *
     * @param date {@link String} representation of the date to convert.
     * @return {@link String} representation of the formatted date.
     */
    private String javaReformattedDate(String date) {
        String[] sections = date.split(" ");
        return sections[0] + "T" + sections[1];
    }

    /**
     * Takes a Java formatted date and converts it into a SQL formatted date.
     * All in String representation.
     *
     * @param date {@link String} representation of the date to convert.
     * @return {@link String} representation of the formatted date.
     */
    private String sqlFormattedDate(String date) {
        String[] sections = date.split("T");
        return sections[0] + " " + sections[1];
    }

    /**
     * Adds an {@link AuctionItemInsert} to the database.
     *
     * @param auctionItemInsert The {@link AuctionItemInsert} to add.
     */
    public void addAuctionItem(AuctionItemInsert auctionItemInsert) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "INSERT INTO AUCTION_ITEMS VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, auctionItemInsert.getName());
            preparedStatement.setString(3, auctionItemInsert.getCategory());
            preparedStatement.setString(4, auctionItemInsert.getDescription());
            preparedStatement.setBigDecimal(5, auctionItemInsert.getCurrentBid());
            preparedStatement.setString(6, this.sqlFormattedDate(auctionItemInsert.getAuctionStartTime().toString()));
            preparedStatement.setString(7, this.sqlFormattedDate(auctionItemInsert.getAuctionEndTime().toString()));
            preparedStatement.setString(8, auctionItemInsert.getSellerId());
            preparedStatement.setString(9, null);
            preparedStatement.setBytes(10, Base64.getEncoder().encode(auctionItemInsert.getImage().getBytes()));

            preparedStatement.executeUpdate();

        } catch (SQLException | IOException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    /**
     * Retrieves all auction items from the database.
     *
     * @return A {@link List} of {@link AuctionItemRetrieve}s.
     */
    public List<AuctionItemRetrieve> getAllItems() {

        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * FROM AUCTION_ITEMS";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AuctionItemRetrieve> auctionItemInserts = new ArrayList<>();

            while (resultSet.next()) {
                AuctionItemRetrieve auctionItemRetrieve = new AuctionItemRetrieve(
                        resultSet.getString("itemID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getBigDecimal("currentBid"),
                        resultSet.getBytes("image"),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucStartTime"))),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucEndTime"))),
                        resultSet.getString("sellerID"),
                        resultSet.getString("bidderID")
                );

                auctionItemInserts.add(auctionItemRetrieve);
                System.out.println(auctionItemRetrieve);
            }

            return auctionItemInserts;
        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves all auction items from the database given the seller's ID.
     *
     * @return A {@link List} of {@link AuctionItemRetrieve}s.
     */
    public List<AuctionItemRetrieve> getAllItems(String sellerId) {

        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * FROM AUCTION_ITEMS WHERE sellerID = ?;";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sellerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AuctionItemRetrieve> auctionItemInserts = new ArrayList<>();

            while (resultSet.next()) {
                AuctionItemRetrieve auctionItemRetrieve = new AuctionItemRetrieve(
                        resultSet.getString("itemID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getBigDecimal("currentBid"),
                        resultSet.getBytes("image"),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucStartTime"))),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucEndTime"))),
                        resultSet.getString("sellerID"),
                        resultSet.getString("bidderID")
                );

                auctionItemInserts.add(auctionItemRetrieve);
                System.out.println(auctionItemRetrieve);
            }

            return auctionItemInserts;
        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves an {@link AuctionItemInsert} given its ID.
     *
     * @param Id The ID of the auction item.
     * @return The retrieved {@link AuctionItemInsert}
     */
    public AuctionItemRetrieve findItemById(String Id) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * FROM AUCTION_ITEMS WHERE itemID = ?";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, Id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new AuctionItemRetrieve(
                        resultSet.getString("itemID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getBigDecimal("currentBid"),
                        resultSet.getBytes("image"),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucStartTime"))),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucEndTime"))),
                        resultSet.getString("sellerID"),
                        resultSet.getString("bidderID")
                );
            } else {
                return null; // Item not found
            }

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
            return null; // Return null or handle the exception as needed.
        }
    }

    /**
     * Retrieves an {@link AuctionItemInsert} given its name.
     *
     * @param name The name of the auction item.
     * @return The retrieved {@link AuctionItemInsert}
     */
    public AuctionItemRetrieve findItemByName(String name) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * FROM AUCTION_ITEMS WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new AuctionItemRetrieve(
                        resultSet.getString("itemID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getBigDecimal("currentBid"),
                        resultSet.getBytes("image"),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucStartTime"))),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucEndTime"))),
                        resultSet.getString("sellerID"),
                        resultSet.getString("bidderID")
                );
            } else {
                return null; // Item not found
            }

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
            return null; // Return null or handle the exception as needed.
        }
    }

    /**
     * Updates an {@link AuctionItemRetrieve}.
     *
     * @param auctionItemInsert The updated {@link AuctionItemInsert}.
     */
    public void updateItem(AuctionItemRetrieve auctionItemInsert) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
                UPDATE AUCTION_ITEMS
                SET name = ?,
                    category = ?,
                    description = ?
                WHERE itemID = ?;
                """.trim();

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, auctionItemInsert.getName());
            preparedStatement.setString(2, auctionItemInsert.getCategory());
            preparedStatement.setString(3, auctionItemInsert.getDescription());
            preparedStatement.setString(4, auctionItemInsert.getItemID());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    /**
     * Updates the bid amount and the user who bid for an {@link AuctionItemInsert}.
     *
     * @param itemName The name of the {@link AuctionItemInsert}.
     * @param bidderID The user who bid on the {@link AuctionItemInsert}.
     * @param bidAmount The new bid amount for the {@link AuctionItemInsert}.
     */
    public void updateBid(String itemName, String bidderID, BigDecimal bidAmount) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
                UPDATE AUCTION_ITEMS
                SET currentBid = ?,
                    bidderID = ?
                WHERE name = ?;             \s
                """.trim();

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setBigDecimal(1, bidAmount);
            preparedStatement.setString(2, bidderID);
            preparedStatement.setString(3, itemName);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    /**
     * Deletes an item from the database given its ID.
     *
     * @param itemId The ID of the item to delete.
     */
    public void deleteItem(String itemId) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "DELETE FROM AUCTION_ITEMS WHERE itemID = ?";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, itemId);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }
}