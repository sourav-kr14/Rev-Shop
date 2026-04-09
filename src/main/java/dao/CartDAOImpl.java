package dao;

import model.Cart;
import util.DBConnection;

import java.sql.*;

public class CartDAOImpl  implements  CartDAO
{
    @Override
    public int createCart(int userId) {

        String query = "INSERT INTO cart(user_id) VALUES(?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, userId);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs.next()) {
                    int cartId = rs.getInt(1);
                    System.out.println("Cart created successfully with ID: " + cartId);
                    return cartId;
                }
            } else {
                System.out.println("Cart creation failed");
            }

        } catch (SQLException e) {
            System.out.println("Error while creating cart for User Id " + userId + " Error: " + e.getMessage());
        }

        return -1;
    }

    @Override
    public Cart getCartByUserId(int userId) {
       String query="Select * from cart where user_id=?";
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
        String query="Delete from cart where cart_id=?";
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
