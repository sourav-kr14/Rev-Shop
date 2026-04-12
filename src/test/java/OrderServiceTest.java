

import dao.*;
import model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.OrderService;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setup() {
        CartDAO cartDAO = new CartDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();

        orderService = new OrderService(
                cartDAO, cartItemDAO, orderDAO, orderItemDAO, productDAO
        );
    }


    @Test
    void testCheckoutSuccess() {

        int userId = 1;

        String input = "Test Address\n1\n";


        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            orderService.checkout(userId);
        });
    }


    @Test
    void testCheckoutInvalidPayment() {

        int userId = 1;

        String input = "Test Address\n5\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            orderService.checkout(userId);
        });
    }


    @Test
    void testCheckoutEmptyCart() {

        int userId = 9999;

        String input = "Test Address\n1\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            orderService.checkout(userId);
        });
    }


    @Test
    void testGetOrderDetails() {

        List<OrderItem> items = orderService.getOrderDetails(1);

        assertNotNull(items);
    }


    @Test
    void testCancelOrder() {

        assertDoesNotThrow(() -> {
            orderService.cancelOrder(1);
        });
    }


    @Test
    void testGetUserOrders() {

        assertNotNull(orderService.getUserOrders(1));
    }
}