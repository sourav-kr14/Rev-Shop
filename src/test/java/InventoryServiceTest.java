

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


    @Test
    void testCheckStockValidProduct() {
        int productId = 4;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        inventoryService.checkStockByProductId(productId);

        String output = out.toString();

        assertTrue(output.contains("Product:"));
        assertTrue(output.contains("Current Stock:"));
    }


    @Test
    void testProductNotFound() {
        int productId = 9999;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        inventoryService.checkStockByProductId(productId);

        String output = out.toString();

        assertTrue(output.contains("Product not found"));
    }


    @Test
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