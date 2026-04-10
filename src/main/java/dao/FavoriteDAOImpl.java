package dao;

import model.Favorite;
import model.Product;
import model.Review;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAOImpl implements  FavoriteDAO{
    @Override
    public void addFavorite(int userId, int productId) {
        String query="Insert into favor(user_id,product_id) values(?,?)";
       try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
       {
           preparedStatement.setInt(1,userId);
           preparedStatement.setInt(2,productId);
           int rowsInserted=preparedStatement.executeUpdate();
           if(rowsInserted>0)
           {
               System.out.println("Added to favorites successfully");
           }
           else {
               System.out.println("Unable to add to favorites");
           }

       }
       catch (SQLException e)
       {
           System.out.println("Error while adding to favorites"+e.getMessage());
       }
    }

    @Override
    public void removeFavorite(int userId, int productId) {
        String query="Delete from favorites where user_id=? and product_id=?";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            int rowsInserted=preparedStatement.executeUpdate();
            if(rowsInserted>0)
            {
                System.out.println("Removed from favorites successfully");
            }
            else {
                System.out.println("Not found in favorites");
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error while adding to favorites"+e.getMessage());
        }

    }

    @Override
    public List<Product> getFavByUserId(int userId) {
        List<Product> productList= new ArrayList<>();
        String query="select p.* from products p join favor f on p.product_id=f.product_id where f.user_id=?";
        try(Connection connection=DBConnection.getConnection(); PreparedStatement preparedStatement= connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next())
            {
                productList.add(extractFavorite(resultSet));
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error getting details of the items");
        }
        return  productList;

    }

    @Override
    public boolean isFavorite(int userId, int productId) {
        String query="select count(*) from favor where user_id=? and product_id=?";
        try(Connection connection=DBConnection.getConnection(); PreparedStatement preparedStatement= connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            ResultSet resultSet=preparedStatement.executeQuery();
           if(resultSet.next())
           {
               return resultSet.getInt(1)>0;
           }

        }
        catch (SQLException e)
        {
            System.out.println("Error getting details of the items");
        }
        return false;

    }
    Product extractFavorite(ResultSet resultSet) throws SQLException
    {
        return  new Product(resultSet.getInt("product_id"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getDouble("price"),resultSet.getDouble("mrp"),resultSet.getString("category"),resultSet.getInt("stock"),resultSet.getInt("seller_id"),resultSet.getInt("threshold_stock"));
    }
}
