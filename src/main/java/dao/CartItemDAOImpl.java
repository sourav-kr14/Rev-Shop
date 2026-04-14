package dao;

import model.Cart;
import model.CartItem;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CartItemDAOImpl implements CartItemDAO {
    @Override
    public void addCart(CartItem item) {

        CartItem present = getCartItem(item.getCartId(), item.getProductId());
        if (present != null) {
            int newQuantity = present.getQuantity() + item.getQuantity();
            updateQuantity(item.getCartId(), item.getProductId(),newQuantity);
        } else {
            String query = "Insert into cartitems(cart_id,product_id,quantity) values(?,?,?)";
            try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, item.getCartId());
                preparedStatement.setInt(2, item.getProductId());
                preparedStatement.setInt(3, item.getQuantity());
                int rowInserted = preparedStatement.executeUpdate();
                if (rowInserted > 0) {
                    System.out.println("Added to cart successfully");
                } else {
                    System.out.println("Unable to insert in cart");
                }
            } catch (SQLException e) {
                System.out.println("Error while inserting in cart   " + e.getMessage());
            }


        }

    }

    @Override
    public void updateQuantity(int cartId, int productId, int quantity) {

        String query="Update cartitems set quantity=? where cart_id=? and product_id=?";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,quantity);
            preparedStatement.setInt(2,cartId);
            preparedStatement.setInt(3,productId);
            int rowInserted=preparedStatement.executeUpdate();
            if(rowInserted>0)
            {
                System.out.println("Cart Update Successfully");
            }
            else
            {
                System.out.println("Unable to update  cart");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while inserting in cart   "+e.getMessage());
        }
    }


    @Override
    public List<CartItem> getCartItems(int cartId) {
      List<CartItem> items=new ArrayList<>();
      String query="Select * from cartitems where cart_id=?";
      try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
      {
          preparedStatement.setInt(1,cartId);
          ResultSet resultSet=preparedStatement.executeQuery();
          while (resultSet.next())
          {
              items.add(extractItems(resultSet));
          }
      }
      catch (SQLException e)
      {
          System.out.println("Error getting items" +e.getMessage());

      }
      return items;
    }
    CartItem extractItems(ResultSet resultSet) throws SQLException {
        return  new CartItem(resultSet.getInt("cart_item_id"),resultSet.getInt("cart_id"),resultSet.getInt("product_id"),resultSet.getInt("quantity"));
    }

    @Override
    public void emptyCart(Connection connection,int cartId) {
        String query="Delete from cartitems where cart_id=?  ";
        try(PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,cartId);

            preparedStatement.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("Error   "+e.getMessage());
        }


    }

    @Override
    public CartItem getCartItem(int cartId, int productId) {
        String query = "Select * from cartitems where cart_id=? and product_id=?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet=preparedStatement.executeQuery();
           while(resultSet.next())
           {
               return extractItems(resultSet);
           }
        } catch (SQLException e) {
            System.out.println("Error while inserting in cart   " + e.getMessage());
        }
        return null;
    }

    @Override
    public void removeItem(int cartId,int productId) {
        String query="Delete from cartitems where cart_id=? and product_id=? ";
        try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
        {

            preparedStatement.setInt(1,cartId);
            preparedStatement.setInt(2,productId);
            int rowsUpdated=preparedStatement.executeUpdate();
            if(rowsUpdated>0)
            {
                System.out.println("Items removed from cart");
            }
            else
            {
                System.out.println("Item not found");

            }
        }
        catch (SQLException e)
        {
            System.out.println("Error removing item from cart"+e.getMessage());
        }
    }


}
