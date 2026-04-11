package service;


import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean register(String email,String password,String role,String securityQuestion,String securityAnswer)
    {
        User user= new User(0,email,password,role,securityQuestion,securityAnswer);
        return userDAO.register(user);

    }
    public  User login(String email,String password) {
        return userDAO.login(email, password);
    }

    public boolean changePassword(String email,String oldPassword,String newPassword)
    {

        User user=userDAO.login(email,oldPassword);

        if(user != null)
        {
            return userDAO.resetPassword(email,newPassword);
        }
        return false;


    }


    public String getSecurityQuestion(String email)
    {
        return userDAO.getSecurityQuestion(email);
    }
    public boolean verifySecurityAnswer(String email,String answer)
    {
        String dbAnswer=userDAO.getSecurityAnswer(email);

        return dbAnswer != null && dbAnswer.equalsIgnoreCase(answer.trim());
    }


    public boolean resetPassword(String email,String newPassword)
    {
        return userDAO.resetPassword(email, newPassword);
    }
}
