package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.WishlistItem;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Interacts with the database to store, retrieve, and delete {@link WishlistItem}s.
 */
@Service
public class WishlistDao {
    /**
     * Saves/stores a {@link WishlistItem} within the database.
     *
     * @param item The {@link WishlistItem} to store
     */
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

    /**
     * Deletes a given {@link WishlistItem} from the database.
     *
     * @param item The {@link WishlistItem} to delete
     */
    public void deleteItem(WishlistItem item) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = """
                    DELETE FROM WISHLIST
                    WHERE itemID = ?
                    AND userID = ?
                    AND currentBid = ?
                    AND reason = ?
                """;

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

    /**
     * Retrieves a {@link WishlistItem} from the database given its itemId, userId, and reason.
     *
     * @param itemId The ID of the item that this wishlist item represents
     * @param userId The ID of the user who saved this wishlist item
     * @param reason The reason the user saved this wishlist item ("BID" or "SAVED")
     * @return The retrieved {@link WishlistItem}
     */
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

    /**
     * Retrieves all {@link WishlistItem}s within the database.
     *
     * @return A {@link List} of all {@link WishlistItem}s in the database
     */
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

    /**
     * Retrieves all {@link WishlistItem}s that are associated with a given user.
     *
     * @param userId The user ID of the user to retrieve items for
     * @return A {@link List} of all {@link WishlistItem}s associated with the user
     */
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
