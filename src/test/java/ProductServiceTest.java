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


    @Test
    @Order(1)
    @DisplayName("Test to add product")
    void GivenCorrectInput_WhenUserAddProduct_ThenProductAddSuccess()
    {
        String name="test_product";
        productService.addProduct(name,"bestproduct",100,120,"test",10,1,5);
        List<Product> productList= productDAO.getAllProducts();
        boolean product_found=productList.stream().anyMatch(p->p.getName().equalsIgnoreCase(name));
        Product added_test = productList.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        assertTrue(product_found);
        assertNotNull(added_test);
        assertEquals("test_product",added_test.getName());
        assertEquals("best product",added_test.getDescription());
        assertEquals(100,added_test.getPrice());
        assertEquals(120,added_test.getMrp());
        assertEquals(10,added_test.getStock());
    }


    @Test
    @Order(2)
    @DisplayName("Test to update product")
    void GivenUpdatedInput_WhenUserUpdateProduct_ThenProductUpdateSuccess()
    {
        List<Product> productList= productDAO.getAllProducts();
        assertFalse(productList.isEmpty());
        Product product= productList.getFirst();
        product.setName("New_Name");
        productService.updateProduct(product);
        Product updated=productDAO.getProductById(product.getProductId());
        assertEquals("New_name",updated.getName());
        assertEquals(product.getProductId(),updated.getProductId());
        assertNotNull(updated);
    }

    @Test
    @Order(3)
    @DisplayName("Test to update stock")
    void GivenUpdatedStock_WhenUserUpdateStock_ThenStockUpdateSuccess() {
        List<Product> products = productDAO.getAllProducts();
        assertFalse(products.isEmpty());
        Product product = products.get(0);
        int newStock = product.getStock() + 5;
        productService.updateStock(product.getProductId(), newStock);
        Product updated = productDAO.getProductById(product.getProductId());
        assertEquals(newStock, updated.getStock());
        assertNotNull(updated);
        assertEquals(newStock,updated.getStock());
        assertTrue(updated.getStock()>=0);
    }


    @Test
    @Order(4)
    @DisplayName("Test to delete product")
    void GivenProductId_WhenUserDeleteProduct_ThenProductDeleteSuccess() {
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
    void GivenSearchInput_WhenUserSearchProduct_ThenProductSearchSuccess() {
        String unique = "SearchProduct";
        productService.addProduct(unique, "desc", 200, 250, "category", 10, 1, 2);
        List<Product> results = productDAO.searchProducts(unique);
        boolean found = results.stream()
                .anyMatch(p -> p.getName().equals(unique));
        assertTrue(found);
        assertFalse(results.isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Test to check invalid price")
    void givenInvalidPrice_WhenAddProduct_ThenFail()
    {
        assertThrows(IllegalArgumentException.class,()->
        {
            productService.addProduct("test_product","test_desc",-100,-120,"test",10,2,4);
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test to check invalid stock")
    void givenInvalidStock_WhenAddProduct_ThenFail()
    {
        assertThrows(IllegalArgumentException.class,()->
        {
            productService.addProduct("test_product","test_desc",100,120,"test",-9,2,4);
        });
    }

    @Test
    @Order(8)
    @DisplayName("Test to check update for invalid product")
    void givenInvalidProduct_WhenUserUpdate_ThenFail()
    {
        Product product=new Product();
        product.setProductId(-9999);
        assertThrows(Exception.class,()->
        {
            productService.updateProduct(product);
        });
    }
    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Product Service Test Suite ====");
    }
}
