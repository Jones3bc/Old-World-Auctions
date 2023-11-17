package com.example.cps410proto.modules.services;

import com.example.cps410proto.modules.data.LoginDao;
import com.example.cps410proto.modules.models.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    private LoginDao loginDao = new LoginDao(); // Instantiate LoginDao for database interaction

    public User createAccount(String username, String password) throws Exception {

        User newUser = new User(username, password);
        if (loginDao.isLoginInfoValid(newUser)) {
            //Database storage
             loginDao.insertUser(newUser);

            return newUser;

        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }


}
