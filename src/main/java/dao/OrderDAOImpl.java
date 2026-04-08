package dao;

import model.Order;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements  OrderDAO {
    @Override
    public int placeOrder(Order order)
    {
        int orderId = 0;

        String query = "INSERT INTO orders (user_id, total_amount, status, order_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(order.getOrderDate()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next())
            {
                orderId = rs.getInt(1);
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error placing order " + e.getMessage());
        }

        return orderId;
    }

    @Override
    public List<Order> getOrderByUserId(int userId) {
        List<Order> user=new ArrayList<>();
        String query="Select * from orders where userid=?";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setInt(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next())
            {
                user.add(extractOrder(resultSet));
            }


        }
        catch (SQLException e)
        {
            System.out.println("Error getting user "+e.getMessage());
        }
        return user;

    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> user=new ArrayList<>();
        String query="Select * from orders";
        try (Connection connection=DBConnection.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query); ResultSet resultSet=preparedStatement.executeQuery()){

            while (resultSet.next())
            {
                user.add(extractOrder(resultSet));
            }


        }
        catch (SQLException e)
        {
            System.out.println("Error getting user "+e.getMessage());
        }
        return user;

    }


    private Order extractOrder(ResultSet resultSet) throws SQLException
    {
        return new Order(resultSet.getInt("orderid"),resultSet.getInt("userid"),resultSet.getDouble("totalamount"),resultSet.getString("status"),resultSet.getTimestamp("orderdate").toLocalDateTime());
    }
}
