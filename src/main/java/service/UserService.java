package service;


import dao.UserDAO;
import exception.*;
import model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean register(String email,String password,String role,String securityQuestion,String securityAnswer)
    {
        User existing=userDAO.login(email,password);
        if(existing != null)
        {
            throw  new UserAlreadyExistsException("User already exists with email :"+email);
        }
        User user= new User(0,email,password,role,securityQuestion,securityAnswer);
        boolean success= userDAO.register(user);
        if(!success)
        {
            throw  new UserException("Failed to register new user");
        }
        return true;

    }
    public  User login(String email,String password) {
        User user= userDAO.login(email, password);
        if(user == null)
        {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return user;
    }

    public boolean changePassword(String email,String oldPassword,String newPassword)
    {

        User user=userDAO.login(email,oldPassword);
        if(user == null)
        {
            System.out.println("Old password is wrong");
        }

       boolean success= userDAO.resetPassword(email,newPassword);
        if(!success)
        {
            throw new UserException("Failed to change password");
        }
        return true;
    }


    public String getSecurityQuestion(String email)
    {
        String question= userDAO.getSecurityQuestion(email);
        if(question == null)
        {
            throw new UserNotFoundException("User doesn't exist with email   "+email);
        }
        return question;
    }
    public boolean verifySecurityAnswer(String email,String answer)
    {
        String dbAnswer=userDAO.getSecurityAnswer(email);
        if(dbAnswer== null)
        {
            throw new UserNotFoundException("User not found with email:"+email);
        }

       if(!dbAnswer.equalsIgnoreCase(answer.trim()))
       {
           throw new InvalidSecurityAnswerException("Incorrect Security Answer");
       }
       return true;
    }


    public boolean resetPassword(String email,String newPassword)
    {
        boolean success= userDAO.resetPassword(email, newPassword);
        if(!success)
        {
            throw new UserNotFoundException("Password failed to reset");
        }
        return true;
    }
}
