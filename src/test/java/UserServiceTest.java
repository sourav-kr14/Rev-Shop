import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void begin()
    {
        UserDAO userDAO= new UserDAOImpl();
        userService=new UserService(userDAO);
    }

    @Test
    void testRegister()
    {
        boolean result= userService.register("test5@gmail.com","test@123","buyer","phone","samsung");
        assertTrue(result);
    }
    @Test
    void testLogin()
    {
        userService.register("test3@gmail.com","test@123","buyer","phone","samsung");
        User user= userService.login("test3@gmail.com","test@123");

        assertEquals("test3@gmail.com",user.getEmail());
        assertEquals("test@123",user.getPassword());
        assertNotNull(user);
    }
    @Test
    void testWrongPassword()
    {
        User user= userService.login("test@gmail.com","test@111");
        assertNull(user);
    }
    @Test
    void testChangePassword()
    {
        userService.register("test4@gmail.com","test@123","buyer","phone","samsung");
        boolean result= userService.changePassword("test4@gmail.com","test@123","test@111");
        assertTrue(result);

    }
    @Test
    void testChangeWrongPassword()
    {
        userService.register("test@gmail.com","test@123","buyer","phone","samsung");
        boolean result= userService.changePassword("test@gmail.com","test@999","test@111");
        assertFalse(result);
    }
    @Test
    void testGetSecurityQuestion()
    {
        userService.register("test@gmail.com","test@123","buyer","phone","samsung");
        String question = userService.getSecurityQuestion("test@gmail.com");
        assertEquals("phone",question);
    }
    @Test
    void testGetSecurityAnswer()
    {
        userService.register("test6@gmail.com","test@123","buyer","phone","samsung");
        boolean result= userService.verifySecurityAnswer("test6@gmail.com","samsung");
        assertTrue(result);
    }
    @Test
    void testGetSecurityAnswerWrong()
    {
        boolean result= userService.verifySecurityAnswer("test@gmail.com","oneplus");
        assertFalse(result);
    }
    @Test
    void testResetPassword()
    {
        boolean result= userService.resetPassword("test@gmail.com","test@000");
    }

}
