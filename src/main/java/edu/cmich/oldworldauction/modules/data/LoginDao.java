package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Interacts with the database to store, update, and retrieve {@link User} information.
 */
@Service
public class LoginDao {
    /**
     * Inserts a new user into the database.
     *
     * @param user The {@link User} to insert into the database.
     */
    public void insertUser(User user) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "INSERT INTO USERS VALUES (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }

    /**
     * Retrieves all users from the database
     *
     * @return A {@link List} of {@link User}s.
     */
    public List<User> retrieveUsers(){
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "SELECT * from USERS;";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> userList = new ArrayList<>();
            while(resultSet.next()){
                userList.add(new User(resultSet.getString("userID"), resultSet.getString("username"), resultSet.getString("password")));
            }

            return userList;

        } catch (SQLException ex){
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }

        return Collections.emptyList();
    }

    /**
     * Updates a {@link User} in our database with the given {@link User} information.
     *
     * @param updatedUser {@link User} that holds updated user information.
     */
    public void updateUser(User updatedUser) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "UPDATE USERS SET username = ?, password = ? WHERE userID = ?;";

        try {
            Connection connection = DriverManager.getConnection(sqlConnection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, updatedUser.getUsername());
            preparedStatement.setString(2, updatedUser.getPassword());
            preparedStatement.setString(3, updatedUser.getUserID());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }
    }
}
