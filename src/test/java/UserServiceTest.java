import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;
import org.junit.jupiter.api.*;
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
    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished User Service Test Suite ====");
    }
    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting User Service Test Suite ====");
    }


    @Test
    @Order(1)
    @DisplayName("Test to register new User")
    void testRegister()
    {
        boolean result= userService.register("test5@gmail.com","test@123","buyer","phone","samsung");
        assertTrue(result);
    }
    @Test
    @Order(2)
    @DisplayName("Test to login")
    void testLogin()
    {
        userService.register("test3@gmail.com","test@123","buyer","phone","samsung");
        User user= userService.login("test3@gmail.com","test@123");

        assertEquals("test3@gmail.com",user.getEmail());
        assertEquals("test@123",user.getPassword());
        assertNotNull(user);
    }
    @Test
    @Order(3)
    @DisplayName("Test to check wrong password")
    void testWrongPassword()
    {
        User user= userService.login("test@gmail.com","test@111");
        assertNull(user);
    }
    @Test
    @Order(4)
    @DisplayName("Test to change password")
    void testChangePassword()
    {
        userService.register("test4@gmail.com","test@123","buyer","phone","samsung");
        boolean result= userService.changePassword("test4@gmail.com","test@123","test@111");
        assertTrue(result);

    }
    @Test
    @Order(5)
    @DisplayName("Test to change with wrong old password")
    void testChangeWrongPassword()
    {
        userService.register("test@gmail.com","test@123","buyer","phone","samsung");
        boolean result= userService.changePassword("test@gmail.com","test@999","test@111");
        assertFalse(result);
    }
    @Test
    @Order(6)
    @DisplayName("Test to get security questions")
    void testGetSecurityQuestion()
    {
        userService.register("test@gmail.com","test@123","buyer","phone","samsung");
        String question = userService.getSecurityQuestion("test@gmail.com");
        assertEquals("phone",question);
    }
    @Test
    @Order(7)
    @DisplayName("Test to get security answer")
    void testGetSecurityAnswer()
    {
        userService.register("test6@gmail.com","test@123","buyer","phone","samsung");
        boolean result= userService.verifySecurityAnswer("test6@gmail.com","samsung");
        assertTrue(result);
    }
    @Test
    @Order(8)
    @DisplayName("Test to get wrong security answer")
    void testGetSecurityAnswerWrong()
    {
        boolean result= userService.verifySecurityAnswer("test@gmail.com","oneplus");
        assertFalse(result);
    }
    @Test
    @Order(9)
    @DisplayName("Test to reset password")
    void testResetPassword()
    {
        boolean result= userService.resetPassword("test@gmail.com","test@000");
    }

}
