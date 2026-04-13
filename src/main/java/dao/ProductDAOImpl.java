package dao;

import model.Product;
import util.DBConnection;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void addProduct(Product product) {
        String query="Insert into products (name,description,price,mrp,category,stock,seller_id,threshold_stock) values(?,?,?,?,?,?,?,?)";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setDouble(4,product.getMrp());
            preparedStatement.setString(5,product.getCategory());
            preparedStatement.setInt(6,product.getStock());
            preparedStatement.setInt(7,product.getSellerId());
            preparedStatement.setInt(8,product.getThreshold());

            int rowsInserted=preparedStatement.executeUpdate();
            if(rowsInserted>0)
            {
                System.out.println("Product inserted successfully");
            }
            else
            {
                System.out.println("Something went wrong");
            }

        }
        catch (Exception e)
        {
            System.out.println("Error while inserting products  "+e.getMessage());
        }
    }


    @Override
    public List<Product> getAllProducts() {
        List<Product> products=new ArrayList<>();
        String sql= "Select * from products";
        try(Connection connection= DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(sql);ResultSet resultSet=preparedStatement.executeQuery())
        {
            while (resultSet.next())
            {
                products.add(extractProduct(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching all products   "+e.getMessage());
        }
        return products;
    }

    @Override
    public Product getProductById(int id) {
       String query="Select * from products where product_id=?";
       try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
       {
           preparedStatement.setInt(1,id);
           try(ResultSet resultSet=preparedStatement.executeQuery())
           {
               if(resultSet.next())
               {
                   return extractProduct(resultSet);
               }
           }
       }
       catch (SQLException e)
       {
           System.out.println("Error while fetching product id"+e.getMessage());
       }
       return null;
    }

    @Override
    public void updateProduct(Product product) {
        String sql= "Update products set name =?,description=?,price=?,mrp=?,category=?,stock=? where product_id=?";
        try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(sql))
        {
          preparedStatement.setString(1,product.getName());
          preparedStatement.setString(2,product.getDescription());
          preparedStatement.setDouble(3,product.getPrice());
          preparedStatement.setDouble(4,product.getMrp());
          preparedStatement.setString(5,product.getCategory());
          preparedStatement.setInt(6,product.getStock());
          preparedStatement.setInt(7,product.getProductId());
          int rowsUpdated=preparedStatement.executeUpdate();
          if(rowsUpdated>0)
          {
              System.out.println("Product updated successfully");
          }
          else
          {
              System.out.println("Update failed");
          }
        }
        catch (SQLException e)
        {
            System.out.println("Error while updating products "+e.getMessage());
        }

    }

    @Override
    public boolean deleteProduct(int id) {
        String sql="Delete from products where product_id=?";
        try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1,id);
            int rows=preparedStatement.executeUpdate();
            if(rows>0)
            {
                System.out.println("Deleted successfully");
            }
            else {
                System.out.println("Delete operation failed");
            }
            return rows>0;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> searchProducts(String keyword) {
       List<Product> search= new ArrayList<>();
       String sql= "Select * from products where name like ? or category like ? or description like ?";
       try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(sql))
       {
           preparedStatement.setString(1,"%" +keyword +"%");
           preparedStatement.setString(2,"%"+keyword+"%");
           preparedStatement.setString(3,"%"+keyword+"%");
           ResultSet resultSet=preparedStatement.executeQuery();
           while (resultSet.next())
           {
               search.add(extractProduct(resultSet));
           }

       }
       catch (SQLException e)
       {
           System.out.println("Error while searching products   "+e.getMessage());
       }
       return  search;
    }

    @Override
    public void updateStock(int productId, int newStock) {
        String query="Update products set stock=?  where product_id=?";
        try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,newStock);
            preparedStatement.setInt(2,productId);

            int rowsUpdated=preparedStatement.executeUpdate();
            if(rowsUpdated>0)
            {
                System.out.println("Stock Updated Successfully");
            }
            else
            {
                System.out.println("Unable to update product with ProductId "+productId);
            }


        }
        catch (SQLException e)
        {
            System.out.println("Error updating stock"+e.getMessage());
        }
    }

    Product extractProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("product_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDouble("price"),
                resultSet.getDouble("mrp"),
                resultSet.getString("category"),
                resultSet.getInt("stock"),
                resultSet.getInt("seller_id"),resultSet.getInt("threshold_stock")
        );
    }
}
