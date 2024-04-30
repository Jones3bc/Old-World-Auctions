package edu.cmich.oldworldauction.modules.models;

/**
 * Represents a User.
 */
public class User {
    private final String userID;
    private final String username;
    private final String password;

    /**
     * Primary constructor for this class.
     *
     * @param userID The ID of the user
     * @param username The username of the user
     * @param password The password of the user
     */
    public User(String userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserID() { return userID; }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
