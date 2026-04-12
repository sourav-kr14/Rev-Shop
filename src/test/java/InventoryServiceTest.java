

import model.Product;
import org.junit.jupiter.api.*;
import service.InventoryService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {

    private InventoryService inventoryService;

    @BeforeEach
    void setup() {
        inventoryService = new InventoryService();
    }

    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Inventory Service Test Suite ====");
    }

    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Inventory Service Test Suite ====");

    }

    @Test
    @Order(1)
    @DisplayName("Test to check valid stock by product id")
    void testCheckStockValidProduct() {
        int productId = 4;

        Product product = inventoryService.getProductStockDetails(productId);

        assertNotNull(product);
        assertEquals(productId, product.getProductId());

        assertTrue(product.getStock() >= 0);
        assertTrue(product.getThreshold() >= 0);
    }


    @Test
    @Order(2)
    @DisplayName("Test to check product is not found with wrong product id ")
    void testProductNotFound() {
        int productId = 9999;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        inventoryService.checkStockByProductId(productId);

        String output = out.toString();

        assertTrue(output.contains("Product not found"));
    }


    @Test
    @Order(3)
    @DisplayName("Test to get  low stock with product id ")
    void testLowStock() {
        int productId = 4;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        inventoryService.checkStockByProductId(productId);

        String output = out.toString();

        assertTrue(
                output.contains("Stock is low") ||
                        output.contains("Stock is sufficient")
        );
    }
}