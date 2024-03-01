package edu.cmich.oldworldauction.modules.services;

import edu.cmich.oldworldauction.modules.data.LoginDao;
import edu.cmich.oldworldauction.modules.models.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    private LoginDao loginDao = new LoginDao(); // Instantiate LoginDao for database interaction

    public User createAccount(String username, String password) throws Exception {

        User newUser = new User(UUID.randomUUID().toString(),username, password);
        if (loginDao.isLoginInfoValid(newUser)) {
            //Database storage
             loginDao.insertUser(newUser);

            return newUser;

        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }


}
