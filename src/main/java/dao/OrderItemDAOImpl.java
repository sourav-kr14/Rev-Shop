package dao;

import model.Order;
import model.OrderItem;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO
{

    @Override
    public void addOrderItem(OrderItem orderItem) {
        String query="Insert into order_items(order_id,product_id,quantity,price) values(?,?,?,?)";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,orderItem.getOrderItemId());
            preparedStatement.setInt(2,orderItem.getProductId());
            preparedStatement.setInt(3,orderItem.getQuantity());
            preparedStatement.setDouble(4,orderItem.getPrice());
            int rowsInserted=preparedStatement.executeUpdate();
            if(rowsInserted>0)
            {
                System.out.println("Single order item inserted");
            }
            else
            {
                System.out.println("Failed to insert");
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error "+e.getMessage());
        }

    }

    @Override
    public void addOrderItems(List<OrderItem> items) {

        String query="Insert into order_items(order_id,product_id,quantity,price) values(?,?,?,?)";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            for(OrderItem item:items)
            {
                preparedStatement.setInt(1,item.getOrderItemId());
                preparedStatement.setInt(2,item.getProductId());
                preparedStatement.setInt(3,item.getQuantity());
                preparedStatement.setDouble(4,item.getPrice());
                preparedStatement.addBatch();
            }


            int[] rowsInserted=preparedStatement.executeBatch();
            if(rowsInserted.length>0)
            {
                System.out.println("Single order item inserted");
            }
            else
            {
                System.out.println("Failed to insert");
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error "+e.getMessage());
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
       List<OrderItem>  items=new ArrayList<>();
       String query="Select * from order_items where id=?";
       try(Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(query))
       {
           preparedStatement.setInt(1,orderId);
           ResultSet resultSet=preparedStatement.executeQuery();
           while (resultSet.next())
           {
               items.add(extractOrder(resultSet));

           }
       }
       catch (SQLException e)
       {
           System.out.println("Error while fetching details id "+e.getMessage());
       }
       return items;
    }

    @Override
    public void deleteOrderItemsByOrderId(int orderId) {
        String query="Delte from order_items where order_id=?";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setInt(1,orderId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error while deleting "+e.getMessage());
        }

    }

    private OrderItem extractOrder(ResultSet resultSet) throws SQLException
    {
        return new OrderItem(resultSet.getInt("orderitemid"),resultSet.getInt("orderid"),resultSet.getInt("productid"),resultSet.getInt("quantity"),resultSet.getDouble("price"));
    }
}
