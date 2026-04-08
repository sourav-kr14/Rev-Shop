package dao;

import model.Cart;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDAOImpl  implements  CartDAO
{
    @Override
    public int createCart(int userId) {
       String query="Insert into carts(userId) values(?) ";
       try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
       {
           preparedStatement.setInt(1,userId);
           int rowsInserted=preparedStatement.executeUpdate();
           if(rowsInserted>0)
           {
               System.out.println("Cart created successfully");
           }
           else
           {
               System.out.println("Cart creation failed");
           }


       }
       catch (SQLException e)
       {
           System.out.println("Error while creating cart for User Id"+userId +"Error Message"+e.getMessage());
       }
       return -1;
    }

    @Override
    public Cart getCartByUserId(int userId) {
       String query="Select * from carts where userid=?";
       try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
       {
           preparedStatement.setInt(1,userId);
           ResultSet resultSet=preparedStatement.executeQuery();
           while(resultSet.next())
           {
               return  new Cart(resultSet.getInt("cart_id"),resultSet.getInt("user_id"));
           }
       }
       catch (SQLException e)
       {
           e.printStackTrace();
       }
       return  null;
    }

    @Override
    public void deleteCart(int cartId) {
        String query="Delete from carts where cartid=?";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setInt(1,cartId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("Error deleting product  "+e.getMessage());
        }

    }

}
