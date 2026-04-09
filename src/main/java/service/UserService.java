package service;


import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean register(String email,String password,String role)
    {
        User user= new User(0,email,password,role);
        return userDAO.register(user);

    }
    public  User login(String email,String password) {
        return userDAO.login(email, password);
    }
}
