package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link LoginDao} class.
 *
 * @author Brock Jones
 */
public class LoginDaoTest {
//    private static final LoginDao loginDao = new LoginDao();
//
//    @ParameterizedTest
//    @ValueSource(strings = {"password", "Password", "Password1", "password1!", "1Er!", "Password!", "1#PASSWORD"})
//    public void invalidPasswordThrowsIllegalArgumentException(String password){
//        User user = new User("validUsername", password); //"validUsername" is a valid username.
//        assertThrows(IllegalArgumentException.class, () -> loginDao.isLoginInfoValid(user));
//    }
//
//    @Test
//    public void nullPasswordThrowsIllegalArgumentException(){
//        User user = new User("validUsername", null);
//        assertThrows(IllegalArgumentException.class, () -> loginDao.isLoginInfoValid(user));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"Password12!", "admiN1!9#", "111CatchPhrase111!", "pass!Word45", "securiT321$", "12word%Pass89", "OldWorldAuction2!"})
//    public void validPasswordReturnsTrue(String password){
//        User user = new User("validUsername", password); //"validUsername" is a valid username.
//        assertTrue(loginDao.isLoginInfoValid(user));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"user", "userna1", "USER", "USERNM3", "USERNAME!!!2", "username#", "!!!!7!!!!##"})
//    public void invalidUsernameThrowsIllegalArgumentException(String username){
//        User user = new User(username, "validPassword1!");
//        assertThrows(IllegalArgumentException.class, () -> loginDao.isLoginInfoValid(user));
//    }
//
//    @Test
//    public void nullUsernameThrowsIllegalArgumentException(){
//        User user = new User(null, "validPassword1!");
//        assertThrows(IllegalArgumentException.class, () -> loginDao.isLoginInfoValid(user));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"username", "USERNAME", "Username1", "12345678", "USER1234", "user1234", "1893User"})
//    public void validUsernameReturnsTrue(String username){
//        User user = new User(username, "validPassword1!");
//        assertTrue(loginDao.isLoginInfoValid(user));
//    }
}
