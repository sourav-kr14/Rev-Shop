import dao.ProductDAO;
import dao.ProductDAOImpl;
import model.Product;
import org.junit.jupiter.api.*;
import service.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductDAO productDAO;

    @BeforeEach
    void begin()
    {
        productDAO=new ProductDAOImpl();
        productService= new ProductService(productDAO);
    }

    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Product Service Test Suite ====");
    }
    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Product Service Test Suite ====");
    }

    @Test
    @Order(1)
    @DisplayName("Test to add product")
    void testAddProduct()
    {
        String name="test_product";
        productService.addProduct(name,"bestproduct",100,120,"test",10,1,5);
        List<Product> productList= productDAO.getAllProducts();
        boolean product_found=productList.stream().anyMatch(p->p.getName().equalsIgnoreCase(name));
        assertTrue(product_found);
    }


    @Test
    @Order(2)
    @DisplayName("Test to update product")
    void testUpdateProduct()
    {
        List<Product> productList= productDAO.getAllProducts();
        assertFalse(productList.isEmpty());
        Product product= productList.get(0);
        product.setName("New_Name");
        productService.updateProduct(product);
        Product updated=productDAO.getProductById(product.getProductId());
        assertEquals(product.getName(),updated.getName());
    }

    @Test
    @Order(3)
    @DisplayName("Test to update stock")
    void testUpdateStock() {
        List<Product> products = productDAO.getAllProducts();
        assertFalse(products.isEmpty());
        Product product = products.get(0);
        int newStock = product.getStock() + 5;
        productService.updateStock(product.getProductId(), newStock);
        Product updated = productDAO.getProductById(product.getProductId());
        assertEquals(newStock, updated.getStock());
    }


    @Test
    @Order(4)
    @DisplayName("Test to delete product")
    void testDeleteProduct() {
        String unique = "DeleteProduct";

        productService.addProduct(unique, "desc", 100, 120, "category", 5, 1, 2);

        List<Product> products = productDAO.getAllProducts();

        Product added = products.stream()
                .filter(p -> p.getName().equals(unique))
                .findFirst()
                .orElse(null);

        assertNotNull(added);

        productService.deleteProduct(added.getProductId());

        Product deleted = productDAO.getProductById(added.getProductId());

        assertNull(deleted);
    }


    @Test
    @Order(5)
    @DisplayName("Test to search product")
    void testSearchProducts() {
        String unique = "SearchProduct";
        productService.addProduct(unique, "desc", 200, 250, "category", 10, 1, 2);
        List<Product> results = productDAO.searchProducts(unique);
        boolean found = results.stream()
                .anyMatch(p -> p.getName().equals(unique));
        assertTrue(found);
    }
}
