package com.example.cps410proto.modules.data;

import com.example.cps410proto.modules.models.User;

/**
 * Interacts with the database to store and retrieve user information.
 *
 * @author Brock Jones
 */
public class LoginDao {

    /**
     * Checks the validity of a given {@link User}. A user is valid if its username and password are valid.
     * A username should be between 6-20 characters and only contain letters and numbers.
     * A password should be between 6-20 characters and can contain letters, numbers, and special characters.
     * The password should have at least one uppercase letter, one lowercase letter, one number, and one special character.
     *
     * @throws IllegalArgumentException if the user is invalid.
     * @param user The {@link User} to check for validity.
     * @return True if the user is valid. Should throw an {@link IllegalArgumentException} otherwise.
     */
    public boolean isLoginInfoValid(User user) throws IllegalArgumentException{
        if(!(user.getUsername().matches("^(?=.{6,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"))){
            throw new IllegalArgumentException("The username is not formatted correctly. " +
                    "Must contain between 6-20 characters and only contain letters and numbers.");
        } else if(!(user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,20}$"))){
            throw new IllegalArgumentException("The password is not formatted correctly. " +
                    "Must contain between 6-20 characters and only contain letters, numbers, and special characters. " +
                    "Must also contain 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.");
        }

        return true;
    }
}
