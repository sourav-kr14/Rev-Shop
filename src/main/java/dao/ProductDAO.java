package dao;
import java.util.List;

import model.Product;

public interface ProductDAO
{
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);

    void updateProduct(Product product);

    boolean deleteProduct(int id);

    List<Product> searchProducts(String keyword);

    void updateStock(int productId, int newStock);
}
