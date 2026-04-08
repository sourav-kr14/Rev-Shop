package dao;

import model.User;

public interface UserDAO
{
    boolean register(User user);

    User login(String email,String password);

}
