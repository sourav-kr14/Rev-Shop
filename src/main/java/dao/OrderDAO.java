package dao;

import model.Order;

import java.util.List;

public interface OrderDAO
{
    public int  placeOrder(Order order);
    List<Order> getOrderByUserId(int userId);
    List<Order> getAllOrders();
}
