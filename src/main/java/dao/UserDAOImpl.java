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
        String query="Insert into users (email,password,role,security_question,security_answer) values(?,?,?,?,?) ";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getRole());
            preparedStatement.setString(4, user.getSecurityQuestion());
            preparedStatement.setString(5, user.getSecurityAnswer());
            int rowsInserted=preparedStatement.executeUpdate();
            if(rowsInserted > 0)
            {
                System.out.println("User registered successfully!!!!!");
                return true;
            }
            else
            {
                System.out.println("Something went wrong");
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error while registering user"+e.getMessage());
            return false;
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
                return new User(resultSet.getInt("user_id"),resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("role"),resultSet.getString("security_question"),resultSet.getString("security_answer"));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Something went wrong"+e.getMessage());
        }
       return null;
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
       String check="Select * from users where email=? and password=?";
       String update="Update users set password=? where email=?";
       try (Connection connection=DBConnection.getConnection()){
           PreparedStatement preparedStatement= connection.prepareStatement(check);
           preparedStatement.setString(1,email);
           preparedStatement.setString(2,oldPassword);
           ResultSet resultSet= preparedStatement.executeQuery();
           if(resultSet.next())
           {
               PreparedStatement updateStatement= connection.prepareStatement(update);
               updateStatement.setString(1,newPassword);
               updateStatement.setString(2,email);
               return updateStatement.executeUpdate()>0;
           }
       }
       catch (SQLException e)
       {
           System.out.println("Error changing password");
       }
       return  false;
    }

    @Override
    public String getSecurityQuestion(String email) {
      String query="Select security_question from users where email=?";
      try(Connection connection= DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
      {
          preparedStatement.setString(1,email);
          ResultSet resultSet=preparedStatement.executeQuery();
          if(resultSet.next())
          {
              return resultSet.getString("security_question");
          }
      }
      catch (SQLException e)
      {
          System.out.println("Unable to get security questions  "+e.getMessage());
          e.printStackTrace();
      }
      return null;
    }

    @Override
    public String getSecurityAnswer(String email) {
        String query="Select security_answer from users where email=?";
        try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setString(1,email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
               return resultSet.getString("security_answer");
            }
        }
        catch (Exception e)
        {
            System.out.println("Unable to verify answer "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        String query="Update users set password=? where email=?";
        try (Connection connection= DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)){

            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,email);
            return preparedStatement.executeUpdate()>0;
        }
        catch (Exception e)
        {
            System.out.println("Error resetting password    "+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
