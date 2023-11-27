package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.AuctionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    public List<AuctionItem> getAllItems() {
        //Should it be like that?
        String sqlConnection = "jdbc:sqlite:resources/oldWorldAuctionDb.db";
        String sql = "SELECT * FROM ITEM_INFO";

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
                        LocalDateTime.parse(resultSet.getString("auctionStartTime")),
                        LocalDateTime.parse(resultSet.getString("auctionEndTime")),
                        resultSet.getString("sellerUser")
                        // Add other properties based on your schema...
                );
                auctionItem.setBidderId(resultSet.getString("bidderUser"));

                auctionItems.add(auctionItem);
            }

            return auctionItems;
        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection.");
            return new ArrayList<>(); // Return an empty list or handle the exception as needed.
        }
    }

}
