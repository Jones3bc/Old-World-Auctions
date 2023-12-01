package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.AuctionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.util.StringUtils;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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

    private String javaReformattedDate(String date){
        String[] sections = date.split(" ");
        return sections[0] + "T" + sections[1];
    }

    private String sqlFormattedDate(String date) {
        String[] sections = date.split("T");
        return sections[0] + " " + sections[1];
    }

    public boolean addAuctionitem(AuctionItem auctionItem){
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

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
            return false;
        }

        return false;
    }

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
                        Base64.getEncoder().encode(resultSet.getBytes("image")),
                        resultSet.getString("color"),
                        resultSet.getInt("manufacturedYear"),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucStartTime"))),
                        LocalDateTime.parse(this.javaReformattedDate(resultSet.getString("aucEndTime"))),
                        resultSet.getString("sellerUser")
                        // Add other properties based on your schema...
                );
                auctionItem.setBidderId(resultSet.getString("bidderUser"));

                auctionItems.add(auctionItem);
            }

            return auctionItems;
        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
            return new ArrayList<>(); // Return an empty list or handle the exception as needed.
        }
    }

}
