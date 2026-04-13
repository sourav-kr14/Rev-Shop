package service;

import dao.*;
import exception.CartEmptyException;
import exception.OrderException;
import model.*;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private ProductDAO productDAO;

    public OrderService(CartDAO cartDAO, CartItemDAO cartItemDAO, OrderDAO orderDAO, OrderItemDAO orderItemDAO, ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.cartItemDAO = cartItemDAO;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.productDAO = productDAO;
    }

    public void checkout(int userId) {
        Connection connection=null;
        try  {
            connection=DBConnection.getConnection();
            connection.setAutoCommit(false);
            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
               throw new CartEmptyException("Cart is empty");
            }
            List<CartItem> cartItemList = cartItemDAO.getCartItems(cart.getCartId());
            if (cartItemList.isEmpty()) {
               throw new CartEmptyException("Cart is empty");
            }
            double total = 0;
            for (CartItem item : cartItemList) {
                Product product = productDAO.getProductById(item.getProductId());
                if(product == null)
                {
                    throw  new OrderException("Product not found");
                }
                total += product.getPrice() * item.getQuantity();
            }
            Scanner sc= new Scanner(System.in);
            System.out.println("Enter Shipping Address");
            String shipping_address =sc.nextLine();
            System.out.println("Select Payment Method");
            System.out.println("1. UPI");
            System.out.println("2. CARD");
            System.out.println("3. CASH ON DELIVERY (COD) ");
            String mode=sc.nextLine().toUpperCase();




            PaymentService paymentService = new PaymentService();
            paymentService.paymentProcess(userId, total,mode);

            Order order = new Order(0, userId, total, "PLACED", LocalDateTime.now(), shipping_address,mode);
            int orderId = orderDAO.placeOrder(order);
            System.out.println("DEBUG Order ID = " + orderId);
            if (orderId == -1) {
                connection.rollback();
               throw new OrderException("Unable to create order");
            }
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : cartItemList) {
                Product product = productDAO.getProductById(cartItem.getProductId());
                if (product.getStock() < cartItem.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product  " + product.getProductId());
                }
                OrderItem orderItem = new OrderItem(0, orderId, cartItem.getProductId(), cartItem.getQuantity(), product.getPrice());
                orderItems.add(orderItem);
                int newStock = product.getStock() - cartItem.getQuantity();
                productDAO.updateStock(product.getProductId(), newStock);
            }
            orderItemDAO.addOrderItems(orderItems);
            cartItemDAO.emptyCart(cart.getCartId());
            connection.commit();
            System.out.println("Order placed successfully!");
            System.out.println("Shipping Address    "+shipping_address);
        } catch (SQLException e) {
            try{
                if(connection !=null) connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Transaction unsuccessfull  " + e.getMessage());
        }
        catch (Exception e)
        {
           throw  new OrderException(e.getMessage());

        }


    }
    public List<OrderItem> getOrderDetails(int orderId)
    {
        return orderItemDAO.getOrderItemsByOrderId(orderId);
    }

    public void cancelOrder(int orderId)
    {
        orderItemDAO.deleteOrderItemsByOrderId(orderId);

    }
    public List<Order> getUserOrders(int userId)
    {
        return orderDAO.getOrderByUserId(userId);
    }
}