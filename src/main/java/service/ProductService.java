package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductService
{
    private ProductDAO productDAO;
    public  ProductService(ProductDAO productDAO)
    {
        this.productDAO=productDAO;
    }
    public void addProduct(String name,String description,double price,double mrp,String category,int stock,int sellerId)
    {
        Product product= new Product(0,name,description,price,mrp,category,stock,sellerId);
        productDAO.addProduct(product);

        System.out.println("Product added successfully");

    }

    public void viewAllProducts()
    {
        List<Product> productList= productDAO.getAllProducts();
        if(productList.isEmpty())
        {
            System.out.println("No products available");
            return;
        }
        for(Product p:productList)
        {
            System.out.println("Product ID  "+p.getProductId() + "Product Name: "+p.getName()+"MRP  "+p.getMrp()+"Category: "+p.getCategory() +"Stock   "+p.getStock());

        }
    }

    public void viewProductById(int productId)
    {
        Product product = productDAO.getProductById(productId);
        if (product == null)
        {
            System.out.println("Product doesn't exist");
            return;
        }
        System.out.println("Details of the Product with Product ID  "+product.getProductId());
        System.out.println("Name:   "+product.getName());
        System.out.println("Description:    "+product.getDescription());
        System.out.println("Price:  "+product.getPrice());
        System.out.println("MRP:    "+product.getMrp());
        System.out.println("Category:   "+product.getCategory());
        System.out.println("Stock:  "+product.getStock());


    }
    public void updateProduct(Product product)
    {
        productDAO.updateProduct(product);
        System.out.println("Product updated successfully");
    }

    public void updateStock(int productId,int newStock)
    {
        productDAO.updateStock(productId,newStock);
    }
    public void deleteProduct(int productId)
    {
        productDAO.deleteProduct(productId);
        System.out.println("Deleted successfully");
    }

    public void searchProducts(String search)
    {
        List<Product> productList= productDAO.searchProducts(search);
        if(productList.isEmpty())
        {
            System.out.println("No products found");
            return;
        }
        for(Product p:productList)
        {
            System.out.println("Product Found");
            System.out.println("ProductID:   "+p.getProductId() +"Name:  "+p.getName() + "Price: "+p.getPrice());
        }
    }

}
