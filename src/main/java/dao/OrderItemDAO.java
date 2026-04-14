package dao;

import model.OrderItem;

import java.sql.Connection;
import java.util.List;

public interface OrderItemDAO
{

    void addOrderItems(Connection connection,List <OrderItem> items);
    List<OrderItem> getOrderItemsByOrderId(int orderId);
    void deleteOrderItemsByOrderId(int orderId);
}
