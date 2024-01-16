package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.AuctionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.util.StringUtils;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Interacts with the database to retrieve and store auction items.
 * Also checks validity of {@link AuctionItem}s.
 */
public class ItemDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemDao() {
    }

    @Autowired
    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Checks the validity of a given {@link AuctionItem}.
     *
     * @param auctionItem The {@link AuctionItem} to check for validity.
     * @return True if the item is valid. Should throw an {@link IllegalArgumentException} otherwise.
     * @throws IllegalArgumentException if the item is invalid.
     */
    public boolean isAuctionItemValid(AuctionItem auctionItem) throws IllegalArgumentException {
        BigDecimal currentBid = auctionItem.getCurrentBid();
        int manufacturedYear = auctionItem.getManufacturedYear();

        if (StringUtils.isEmptyOrWhitespace(auctionItem.getName())
                || StringUtils.isEmptyOrWhitespace(auctionItem.getDescription())
                || StringUtils.isEmptyOrWhitespace(auctionItem.getColor())) {
            throw new IllegalArgumentException("Name, Description, and Color fields must be non-null and not be only whitespace.");
        } else if (currentBid == null || currentBid.compareTo(BigDecimal.ZERO) < 0 || currentBid.scale() != 2) {
            throw new IllegalArgumentException("Current Bid field is invalid. Must be non-null, > 0 and have 2 decimal place values.");
        } else if (manufacturedYear < 0 || manufacturedYear > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Manufactured year field must be > 0 and less than the current year.");
        } else if (auctionItem.getAuctionStartTime() == null || auctionItem.getAuctionEndTime() == null) {
            throw new IllegalArgumentException("Auction start/end times cannot be null");
        }

        return true;
    }

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
     * Adds an {@link AuctionItem} to the database.
     *
     * @param auctionItem The {@link AuctionItem} to add.
     */
    public void addAuctionItem(AuctionItem auctionItem) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "INSERT INTO AUCTION_ITEMS VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auctionItem.getName());
            preparedStatement.setString(2, auctionItem.getDescription());
            preparedStatement.setBigDecimal(3, auctionItem.getCurrentBid());
            preparedStatement.setBytes(4, auctionItem.getImage());
            preparedStatement.setString(5, auctionItem.getColor());
            preparedStatement.setInt(6, auctionItem.getManufacturedYear());
            preparedStatement.setString(7, this.sqlFormattedDate(auctionItem.getAuctionStartTime().toString()));
            preparedStatement.setString(8, this.sqlFormattedDate(auctionItem.getAuctionEndTime().toString()));
            preparedStatement.setString(9, auctionItem.getSellerId());
            preparedStatement.setString(10, null);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    /**
     * Retrieves all auction items from the database.
     *
     * @return A {@link List} of {@link AuctionItem}s.
     */
    public List<AuctionItem> getAllItems() {

        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * FROM AUCTION_ITEMS";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AuctionItem> auctionItems = new ArrayList<>();

            while (resultSet.next()) {
                AuctionItem auctionItem = new AuctionItem(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("currentBid"),
                        resultSet.getBytes("image"),
                        resultSet.getString("color"),
                        resultSet.getInt("manufacturedYear"),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucStartTime"))),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucEndTime"))),
                        resultSet.getString("sellerUser")
                );
                auctionItem.setBidderId(resultSet.getString("bidderUser"));

                auctionItems.add(auctionItem);
            }

            return auctionItems;
        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public AuctionItem findItemByName(String name) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * FROM AUCTION_ITEMS WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new AuctionItem(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("currentBid"),
                        resultSet.getBytes("image"),
                        resultSet.getString("color"),
                        resultSet.getInt("manufacturedYear"),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucStartTime"))),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucEndTime"))),
                        resultSet.getString("sellerUser")
                        // Add other properties based on your schema...
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
     * Deletes an item from the database
     *
     * @param name The name of the item to delete.
     */
    public void deleteItem(String name) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "DELETE FROM AUCTION_ITEMS WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }
}