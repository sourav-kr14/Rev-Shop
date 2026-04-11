package dao;

import model.User;

public interface UserDAO
{
    boolean register(User user);

    User login(String email,String password);

    boolean changePassword(String email,String oldPassword,String newPassword);

    String getSecurityQuestion(String email);

    String getSecurityAnswer(String email);

    boolean resetPassword(String email,String newPassword);



}
