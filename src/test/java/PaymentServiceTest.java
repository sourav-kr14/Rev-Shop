
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PaymentService;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        paymentService = new PaymentService();
    }


    @Test
    void testCODPayment() {
        boolean result = paymentService.paymentProcess(1, 500, "COD");

        assertTrue(result);
    }


    @Test
    void testUPIPayment() {
        boolean result = paymentService.paymentProcess(1, 1000, "UPI");

        assertTrue(result);
    }


    @Test
    void testCardPayment() {
        boolean result = paymentService.paymentProcess(1, 1500, "CARD");

        assertTrue(result);
    }


    @Test
    void testInvalidPaymentMethod() {
        boolean result = paymentService.paymentProcess(1, 500, "BITCOIN");

        assertFalse(result);
    }


    @Test
    void testCaseInsensitive() {
        boolean result = paymentService.paymentProcess(1, 500, "upi");

        assertTrue(result);
    }


    @Test
    void testEmptyMethod() {
        boolean result = paymentService.paymentProcess(1, 500, "");

        assertFalse(result);
    }


    @Test
    void testNullMethod() {
        assertThrows(NullPointerException.class, () -> {
            paymentService.paymentProcess(1, 500, null);
        });
    }
}