
package com.example.cps410proto.modules.services;

import com.example.cps410proto.modules.models.User;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    // Implement methods for account-related operations
    private Map<String, User> userAccounts = new HashMap<>();
    public User createAccount(String username, String password) throws Exception {


        // Check if the username already exists
        if (userAccounts.containsKey(username)) {
            throw new Exception("Username already exists");
        }

        //Create the user by using a constructor
        User user = new User(username, password);

        //Append the user to the map of userAccounts
        userAccounts.put(username, user);

        // Here goes the Database storage

        return user;
    }
    public User changePassword(String username, String newPassword) throws Exception {
        User user = userAccounts.get(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        user = new User(username, newPassword);
        userAccounts.put(username, user);

        // Here goes the Database storage

        return user;
    }
    public void deleteAccount(String username) {
        userAccounts.remove(username);

        // Here goes the Database
    }
}