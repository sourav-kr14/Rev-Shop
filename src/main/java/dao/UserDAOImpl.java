package dao;

import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements  UserDAO{
    @Override
    public boolean register(User user) {
        String query="Insert into users (email,password,role) values(?,?,?,?) ";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getRole());
            int rowsInserted=preparedStatement.executeUpdate();
            if(rowsInserted > 0)
            {
                System.out.println("User registered successfully!!!!!");
            }
            else
            {
                System.out.println("Something went wrong");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error while registering user"+e.getMessage());
        }
    }

    @Override
    public User login(String email, String password) {
        String query="Select * from users where email =? and password=? ";
        try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return new User(resultSet.getInt("user_id"),resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("role"));
            }

        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong"+e.getMessage());
        }
       return null;
    }
}
