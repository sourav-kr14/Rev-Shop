package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Order
{
    private int orderId;
    private  int userId;
    private double totalAmount;
    private  String status;
    private LocalDateTime orderDate;
    private  String shippingAddress;
    public Order()
    {

    };

    public Order(int orderId, int userId, double totalAmount, String status, LocalDateTime orderDate,String shippingAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress=shippingAddress;
    }

    public Order(int orderId, int userId, double total, String placed, Date date) {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString()
    {
        return "Order Details " +" Order Id "+orderId +"User Id "+userId +"Total    "+totalAmount +" Status "+status +"Date "+orderDate +"Shipping Address "+shippingAddress;
    }
}
