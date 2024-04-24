package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.WishlistItem;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WishlistDao {
    public void saveItem(WishlistItem item) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "INSERT INTO WISHLIST VALUES (?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, item.itemId());
            preparedStatement.setString(2, item.userId());
            preparedStatement.setBigDecimal(3, item.currentBid());
            preparedStatement.setString(4, item.reason());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    public void deleteItem(WishlistItem item) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
                    DELETE FROM WISHLIST
                    WHERE itemID = ?
                    AND userID = ?
                    AND reason = ?
                """;

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, item.itemId());
            preparedStatement.setString(2, item.userId());
            preparedStatement.setString(3, item.reason());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    public WishlistItem retrieveItem(String itemId, String userId, String reason) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
                    SELECT * FROM WISHLIST
                    WHERE itemID = ?
                    AND userID = ?
                    AND reason = ?
                """;

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, reason);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new WishlistItem(
                        resultSet.getString("itemId"),
                        resultSet.getString("userId"),
                        resultSet.getBigDecimal("currentBid"),
                        resultSet.getString("reason")
                );
            }

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }

        return null;
    }

    public List<WishlistItem> retrieveAllItems() {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * from WISHLIST;";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<WishlistItem> wishlistItems = new ArrayList<>();
            while (resultSet.next()) {
                wishlistItems.add(
                        new WishlistItem(
                                resultSet.getString("itemID"),
                                resultSet.getString("userID"),
                                resultSet.getBigDecimal("currentBid"),
                                resultSet.getString("reason")
                        )
                );
            }

            return wishlistItems;

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }

        return Collections.emptyList();
    }

    public List<WishlistItem> retrieveAllItems(String userId) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * from WISHLIST WHERE userID = ?;";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<WishlistItem> wishlistItems = new ArrayList<>();
            while (resultSet.next()) {
                wishlistItems.add(
                        new WishlistItem(
                                resultSet.getString("itemID"),
                                resultSet.getString("userID"),
                                resultSet.getBigDecimal("currentBid"),
                                resultSet.getString("reason")
                        )
                );
            }

            return wishlistItems;

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }

        return Collections.emptyList();
    }
}
