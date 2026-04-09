package dao;

import model.OrderItem;

import java.util.List;

public interface OrderItemDAO
{

    void addOrderItems(List <OrderItem> items);
    List<OrderItem> getOrderItemsByOrderId(int orderId);
    void deleteOrderItemsByOrderId(int orderId);
}
