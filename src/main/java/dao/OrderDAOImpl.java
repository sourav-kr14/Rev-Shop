package dao;

import model.Order;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements  OrderDAO {
    @Override
    public int placeOrder(Connection connection,Order order)
    {
        String query = "INSERT INTO orders (user_id, total_amount, status, order_date,shipping_address,payment_method) VALUES (?, ?, ?, ?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(order.getOrderDate()));
            ps.setString(5,order.getShippingAddress());
            ps.setString(6,order.getPaymentMethod());

            int rowsInserted=ps.executeUpdate();
            if(rowsInserted>0)
            {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                {
                    return rs.getInt(1);

                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error placing order " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Order> getOrderByUserId(int userId) {
        List<Order> orders=new ArrayList<>();
        String query="Select * from orders where user_id=?";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setInt(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next())
            {
                orders.add(extractOrder(resultSet));
            }


        }
        catch (SQLException e)
        {
            System.out.println("Error getting user "+e.getMessage());
        }
        return orders;

    }

    @Override
    public List<Order> getAllOrders() {
        List<Order>orders=new ArrayList<>();
        String query="Select * from orders";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query); ResultSet resultSet=preparedStatement.executeQuery()){
            while (resultSet.next())
            {
                orders.add(extractOrder(resultSet));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error getting user "+e.getMessage());
        }
        return orders;
    }


    private Order extractOrder(ResultSet resultSet) throws SQLException
    {
        return new Order(resultSet.getInt("order_id"),resultSet.getInt("user_id"),resultSet.getDouble("total_amount"),resultSet.getString("status"),resultSet.getTimestamp("order_date").toLocalDateTime(),resultSet.getString("shipping_address"),resultSet.getString("payment_method"));
    }
}
