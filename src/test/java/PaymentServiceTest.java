
import org.junit.jupiter.api.*;
import service.PaymentService;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        paymentService = new PaymentService();
    }
    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Payment Service Test Suite ====");
    }
    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Payment Service Test Suite ====");
    }


    @Test
    @Order(1)
    @DisplayName("Test COD Payment")
    void testCODPayment() {
        boolean result = paymentService.paymentProcess(1, 500, "COD");

        assertTrue(result);
    }


    @Test
    @Order(2)
    @DisplayName("Test UPI Payment")
    void testUPIPayment() {
        boolean result = paymentService.paymentProcess(1, 1000, "UPI");

        assertTrue(result);
    }


    @Test
    @Order(3)
    @DisplayName("Test Card Payment")
    void testCardPayment() {
        boolean result = paymentService.paymentProcess(1, 1500, "CARD");

        assertTrue(result);
    }


    @Test
    @Order(4)
    @DisplayName("Test Invalid Payment")
    void testInvalidPaymentMethod() {
        boolean result = paymentService.paymentProcess(1, 500, "BITCOIN");

        assertFalse(result);
    }


    @Test
    @Order(5)
    @DisplayName("Test if user enters nothing")
    void testEmptyMethod() {
        boolean result = paymentService.paymentProcess(1, 500, "");

        assertFalse(result);
    }



}