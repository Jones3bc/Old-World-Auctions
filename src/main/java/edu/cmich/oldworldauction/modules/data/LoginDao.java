package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Interacts with the database to store and retrieve {@link User} information.
 * Also checks the validity of {@link User}s.
 */
@Service
public class LoginDao {
    /**
     * Checks the validity of a given {@link User}. A user is valid if its username and password are valid.
     * A username should be between 8-20 characters and only contain letters and numbers.
     * A password should be between 8-20 characters and can contain letters, numbers, and special characters.
     * The password should have at least one uppercase letter, one lowercase letter, one number, and one special character.
     *
     * @throws IllegalArgumentException if the user is invalid.
     * @param user The {@link User} to check for validity.
     * @return True if the user is valid. Should throw an {@link IllegalArgumentException} otherwise.
     */
    public boolean isLoginInfoValid(User user) throws IllegalArgumentException{
        if(user.getUsername() == null || user.getPassword() == null){
            throw new IllegalArgumentException("The user's username or password is null.");
        } else if(!(user.getUsername().matches("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"))){
            throw new IllegalArgumentException("The username is not formatted correctly. " +
                    "Must contain between 6-20 characters and only contain letters and numbers.");
        } else if(!(user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$"))){
            throw new IllegalArgumentException("The password is not formatted correctly. " +
                    "Must contain between 6-20 characters and only contain letters, numbers, and special characters. " +
                    "Must also contain 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.");
        }

        return true;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user The {@link User} to insert into the database.
     */
    public void insertUser(User user) {
        String sqlConnection = "jdbc:sqlite:src/main/resources/oldWorldAuctionDb.db";
        String sql = "INSERT INTO USERS VALUES (?, ?);";

        try (Connection connection = DriverManager.getConnection(sqlConnection);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

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
                userList.add(new User(resultSet.getString("username"), resultSet.getString("password")));
            }

            return userList;

        } catch (SQLException ex){
            System.out.println("Failed to establish and use SQL connection. " + ex.getMessage());
        }

        return Collections.emptyList();
    }
}
