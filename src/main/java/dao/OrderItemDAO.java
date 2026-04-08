package dao;

import model.OrderItem;

import java.util.List;

public interface OrderItemDAO
{
    void addOrderItem(OrderItem orderItem);
    void addOrderItems(List <OrderItem> items);
    List<OrderItem> getOrderItemsByOrderId(int orderId);
    void deleteOrderItemsByOrderId(int orderId);
}
