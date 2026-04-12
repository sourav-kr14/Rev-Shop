

import dao.*;
import model.OrderItem;
import org.junit.jupiter.api.*;
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

    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Order Service Test Suite ====");
    }
    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Order Service Test Suite ====");
    }



    @Test
    @Order(1)
    @DisplayName("Test to check order placed successfully or not")
    void testCheckoutSuccess() {

        int userId = 1;

        String input = "Test Address";


        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            orderService.checkout(userId);
        });
    }
    @Test
    @Order(2)
    @DisplayName("Test to check invalid payment")
    void testCheckoutInvalidPayment() {

        int userId = 1;

        String input = "Test Address";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            orderService.checkout(userId);
        });
    }

    @Test
    @Order(3)
    @DisplayName("Test to check Empty Card")
    void testCheckoutEmptyCart() {

        int userId = 9999;

        String input = "Test Address\n1\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            orderService.checkout(userId);
        });
    }


    @Test
    @Order(4)
    @DisplayName("Test to get order details ")
    void testGetOrderDetails() {

        List<OrderItem> items = orderService.getOrderDetails(1);

        assertNotNull(items);
    }


    @Test
    @Order(5)
    @DisplayName("Test to cancel order")
    void testCancelOrder() {

        assertDoesNotThrow(() -> {
            orderService.cancelOrder(1);
        });
    }


    @Test
    @Order(6)
    @DisplayName("Test to get user orders by userid")
    void testGetUserOrders() {

        assertNotNull(orderService.getUserOrders(1));
    }
}