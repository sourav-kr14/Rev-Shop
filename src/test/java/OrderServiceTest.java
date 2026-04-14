

import dao.*;
import model.OrderItem;
import org.junit.jupiter.api.*;
import service.OrderService;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Nested
class OrderServiceTest {

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
        String input = "Test Address\nCOD\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        orderService.checkout(1,"Address","COD");
        List<?> orders=orderService.getUserOrders(userId);
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }


    @Test
    @Order(2)
    @DisplayName("Test to check invalid payment")
    void givenWrongPaymentMethod_WhenUserPlaceOrder_ThenOrderPlaceFailure() {
        int userId = 1;
        List<?> orders = orderService.getUserOrders(userId);
        assertTrue(orders.isEmpty());

    }
    @Test
    @Order(3)
    @DisplayName("Test to check Empty Card")
    void givenEmptyCard_WhenUserPlaceOrder_ThenOrderPlaceFailure() {
        int userId = 20;
        orderService.checkout(userId,"Address","COD");
        List<?> orders = orderService.getUserOrders(userId);
        assertTrue(orders.isEmpty());

    }


    @Test
    @Order(4)
    @DisplayName("Test to get order details ")
    void givenOrderId_ThenGetOrderDetailsSuccess() {
        List<OrderItem> items = orderService.getOrderDetails(1);
        assertNotNull(items);
        for(OrderItem orderItem:items)
        {
            assertTrue(orderItem.getQuantity()>0);
            assertTrue(orderItem.getPrice()>0);
        }
    }


    @Test
    @Order(5)
    @DisplayName("Test to cancel order")
    void givenOrderId_ThenCancelDetailsSuccess() {
       int userId=11;
        List<?> orderitems = orderService.getUserOrders(userId);
        assertTrue(orderitems.isEmpty());


    }
    @Test
    @Order(6)
    @DisplayName("Test to get user orders by userid")
    void givenUserId_ThenDisplayOrderSuccess() {
        assertNotNull(orderService.getUserOrders(1));
        orderService.getUserOrders(1);
        assertTrue(true);
    }
    @Test
    @Order(7)
    @DisplayName("Test to place multiple orders with same user id")
    void givenMultipleCartItem_WhenUserCheckout_ThenOrderPlaced()
    {

        orderService.checkout(1,"Address","COD");
        List<OrderItem> items = orderService.getOrderDetails(1);
        assertTrue(items.size() > 1);
    }

    }


    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Order Service Test Suite ====");
    }
}