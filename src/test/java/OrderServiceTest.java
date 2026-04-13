

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




    @Test
    @Order(1)
    @DisplayName("Test to check order placed successfully or not")
    void givenCorrectDetails_WhenUserPlaceOrder_ThenOrderPlaceSuccess() {

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
    void givenWrongPaymentMethod_WhenUserPlaceOrder_ThenOrderPlaceFailure() {
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
    void givenEmptyCard_WhenUserPlaceOrder_ThenOrderPlaceFailure() {
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
    void givenOrderId_ThenGetOrderDetailsSuccess() {
        List<OrderItem> items = orderService.getOrderDetails(1);
        assertNotNull(items);
    }


    @Test
    @Order(5)
    @DisplayName("Test to cancel order")
    void givenOrderId_ThenCancelDetailsSuccess() {
        assertDoesNotThrow(() -> {
            orderService.cancelOrder(1);
        });
    }


    @Test
    @Order(6)
    @DisplayName("Test to get user orders by userid")
    void givenUserId_ThenDisplayOrderSuccess() {

        assertNotNull(orderService.getUserOrders(1));
    }


    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Order Service Test Suite ====");
    }
}