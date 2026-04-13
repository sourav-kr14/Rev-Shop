package service;

import dao.ProductDAO;
import dao.ProductDAOImpl;
import exception.ProductNotFoundException;
import model.Product;

public class InventoryService {
    private ProductDAO  productDAO;

    public InventoryService() {
        this.productDAO = new ProductDAOImpl();
    }


    public void checkStockByProductId(int productId) {

        Product product = productDAO.getProductById(productId);

        if (product == null) {
        throw new ProductNotFoundException("Product not found with exception:"+productId);

        }

        System.out.println("Product: " + product.getName());
        System.out.println("Current Stock: " + product.getStock());
        System.out.println("Threshold: " + product.getThreshold());

        if (product.getStock() < product.getThreshold()) {
            System.out.println("Stock is low. Kindly restock");
        } else {
            System.out.println("Stock is sufficient");
        }
    }

    public Product getProductStockDetails(int productId) {
        Product product= productDAO.getProductById(productId);
        if(product == null)
        {
            throw new ProductNotFoundException("Product not found with id "+productId);
        }
        return  product;
    }
}
