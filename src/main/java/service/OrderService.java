package service;

import dao.*;
import exception.CartEmptyException;
import exception.InvalidQuantityException;
import exception.OrderException;
import model.*;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class OrderService {

    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private ProductDAO productDAO;

    public OrderService(CartDAO cartDAO, CartItemDAO cartItemDAO,
                        OrderDAO orderDAO, OrderItemDAO orderItemDAO,
                        ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.cartItemDAO = cartItemDAO;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.productDAO = productDAO;
    }

    public void checkout(int userId, String address, String mode) {

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            Cart cart = validateCart(userId);
            List<CartItem> items = getCartItems(cart);

            Map<Integer, Product> productMap = validateAndFetchProducts(items);

            double total = calculateTotal(items, productMap);

            int orderId = createOrder(connection, userId, total, address, mode);

            List<OrderItem> orderItems = createOrderItems(items, productMap, orderId);

            updateStock(connection, productMap, items);

            saveOrderItems(connection, orderItems);

            clearCart(connection, cart);


            processPayment(userId, total, mode);

            connection.commit();

            System.out.println("Order placed successfully!");

        } catch (Exception e) {
            rollback(connection);
            throw new OrderException("Checkout failed: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }


    private Cart validateCart(int userId) {
        Cart cart = cartDAO.getCartByUserId(userId);
        if (cart == null) {
            throw new CartEmptyException("Cart is empty");
        }
        return cart;
    }

    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> items = cartItemDAO.getCartItems(cart.getCartId());
        if (items.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }
        return items;
    }

    private Map<Integer, Product> validateAndFetchProducts(List<CartItem> items) {

        Map<Integer, Product> productMap = new HashMap<>();

        for (CartItem item : items) {

            Product product = productDAO.getProductById(item.getProductId());

            if (product == null) {
                throw new OrderException("Product not found");
            }

            if (product.getStock() < item.getQuantity()) {
                throw new InvalidQuantityException(
                        "Insufficient stock for product " + product.getProductId()
                );
            }

            productMap.put(product.getProductId(), product);
        }

        return productMap;
    }


    private double calculateTotal(List<CartItem> items,
                                  Map<Integer, Product> productMap) {

        double total = 0;

        for (CartItem item : items) {
            Product product = productMap.get(item.getProductId());
            total += product.getPrice() * item.getQuantity();
        }

        return total;
    }

    private int createOrder(Connection connection, int userId,
                            double total, String address, String mode) {

        Order order = new Order(
                0, userId, total, "PLACED",
                LocalDateTime.now(), address, mode
        );

        int orderId = orderDAO.placeOrder(connection, order);

        if (orderId == -1) {
            throw new OrderException("Order creation failed");
        }

        return orderId;
    }

    private List<OrderItem> createOrderItems(List<CartItem> items,
                                             Map<Integer, Product> productMap,
                                             int orderId) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : items) {

            Product product = productMap.get(item.getProductId());

            orderItems.add(new OrderItem(
                    0,
                    orderId,
                    item.getProductId(),
                    item.getQuantity(),
                    product.getPrice()
            ));
        }

        return orderItems;
    }

    private void updateStock(Connection connection,
                             Map<Integer, Product> productMap,
                             List<CartItem> items) {

        for (CartItem item : items) {

            Product product = productMap.get(item.getProductId());

            int newStock = product.getStock() - item.getQuantity();

            productDAO.updateStock(
                    product.getProductId(),
                    newStock);
        }
    }

    private void saveOrderItems(Connection connection,
                                List<OrderItem> orderItems) {

        orderItemDAO.addOrderItems(connection, orderItems);
    }

    private void clearCart(Connection connection, Cart cart) {
        cartItemDAO.emptyCart(connection, cart.getCartId());
    }

    private void processPayment(int userId, double total, String mode) {
        PaymentService paymentService = new PaymentService();
        paymentService.paymentProcess(userId, total, mode);
    }


    private void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
                System.out.println("Transaction rolled back!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<OrderItem> getOrderDetails(int orderId) {
        return orderItemDAO.getOrderItemsByOrderId(orderId);
    }

//    public void cancelOrder(int orderId) {
//        orderDAO.updateOrderStatus(orderId, "CANCELLED");
//    }

    public List<Order> getUserOrders(int userId) {
        return orderDAO.getOrderByUserId(userId);
    }
}