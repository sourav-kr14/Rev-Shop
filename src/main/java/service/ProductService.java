package service;

import dao.ProductDAO;
import exception.InvalidProductException;
import exception.ProductException;
import exception.ProductNotFoundException;
import model.Product;

import java.util.List;

public class ProductService
{
    public ProductDAO productDAO;
    public  ProductService(ProductDAO productDAO)
    {
        this.productDAO=productDAO;
    }
    public void addProduct(String name,String description,double price,double mrp,String category,int stock,int sellerId,int threshold_stock)
    {

        if(name == null || name.trim().isEmpty())
        {
            throw new ProductException("Product name cannot be empty");
        }
        if(price < 0 || mrp<0)
        {
            throw new ProductException("Price and MRP must be greater than 0");
        }
        if(stock<0)
        {
            throw  new ProductException("Stock should be greater than 0");
        }
        Product product= new Product(0,name,description,price,mrp,category,stock,sellerId,threshold_stock);
        productDAO.addProduct(product);

//        System.out.println("Product added successfully");

    }

    public void viewAllProducts()
    {
        List<Product> productList= productDAO.getAllProducts();
        if(productList.isEmpty())
        {
            throw new ProductNotFoundException("Product unavailable");
        }
        for(Product p:productList)
        {
            System.out.println("Product ID  "+p.getProductId() + "Product Name: "+p.getName()+"MRP  "+p.getMrp()+"Category: "+p.getCategory() +"Stock   "+p.getStock());
        }
    }

    public Product viewProductById(int productId)
    {
        Product product = productDAO.getProductById(productId);
        if (product == null)
        {
           throw new ProductNotFoundException("Product not found with product id:"+productId);
        }
        System.out.println("Details of the Product with Product ID  "+product.getProductId());
        System.out.println("Name:   "+product.getName());
        System.out.println("Description:    "+product.getDescription());
        System.out.println("Price:  "+product.getPrice());
        System.out.println("MRP:    "+product.getMrp());
        System.out.println("Category:   "+product.getCategory());
        System.out.println("Stock:  "+product.getStock());
        return product;

    }
    public void updateProduct(Product product)
    {
        if(product == null)
        {
            throw new InvalidProductException("Product cannot be null");
        }
        productDAO.updateProduct(product);

    }

    public void updateStock(int productId,int newStock)
    {
        if( newStock <0)
        {
            throw  new InvalidProductException("New Stock cannot be negative");
        }
        Product product= productDAO.getProductById(productId);
        if(product == null)
        {
            throw new ProductNotFoundException("Product not found");
        }


        productDAO.updateStock(productId,newStock);
    }
    public void deleteProduct(int productId)
    {
        Product product=productDAO.getProductById(productId);
        if(product == null)
        {
            throw new ProductNotFoundException("Unable to delete");
        }
        productDAO.deleteProduct(productId);


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
