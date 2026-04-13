package dao;

import model.Order;

import java.sql.Connection;
import java.util.List;

public interface OrderDAO
{
    public int  placeOrder(Connection connection,Order order);
    List<Order> getOrderByUserId(int userId);
    List<Order> getAllOrders();
}
