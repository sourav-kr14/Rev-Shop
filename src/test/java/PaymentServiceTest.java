
import org.junit.jupiter.api.*;
import service.PaymentService;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        paymentService = new PaymentService();
    }


    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Payment Service Test Suite ====");
    }



    @Test
    @Order(1)
    @DisplayName("Test COD Payment")
    void givenValidCOD_WhenPaymentProcessed_ThenReturnsTrue() {
        boolean result = paymentService.paymentProcess(1, 500, "COD");
        assertTrue(result);
    }


    @Test
    @Order(2)
    @DisplayName("Test UPI Payment")
    void givenValidUPI_WhenPaymentProcessed_ThenReturnsTrue() {
        boolean result = paymentService.paymentProcess(1, 1000, "UPI");
        assertTrue(result);
    }


    @Test
    @Order(3)
    @DisplayName("Test Card Payment")
    void givenValidCard_WhenPaymentProcessed_ThenReturnsTrue(){
        boolean result = paymentService.paymentProcess(1, 1500, "CARD");

        assertTrue(result);
    }


    @Test
    @Order(4)
    @DisplayName("Test Invalid Payment")
    void givenInvalidPaymentMethod_WhenPaymentProcessed_ThenReturnsFalse() {
        boolean result = paymentService.paymentProcess(1, 500, "BITCOIN");

        assertFalse(result);
    }


    @Test
    @Order(5)
    @DisplayName("Test if user enters nothing")
    void givenEmptyPaymentMethod_WhenPaymentProcessed_ThenReturnsFalse() {
        boolean result = paymentService.paymentProcess(1, 500, "");
        assertFalse(result);
    }

    @Test
    @Order(6)
    @DisplayName("Test if user enter invalid amount")
    void givenInvalidAmount_WhenPayment_ThenFail()
    {
        boolean result = paymentService.paymentProcess(1, -500, "");
        assertFalse(result);
    }

    @Test
    @Order(7)
    @DisplayName("Test if user enters amount 0")
    void givenZeroAmount_WhenPayment_ThenFail()
    {
        boolean result = paymentService.paymentProcess(1, 0, "");
        assertFalse(result);
    }


    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Payment Service Test Suite ====");
    }




}