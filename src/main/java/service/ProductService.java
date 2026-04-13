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
        System.out.println("========= All products ========= ");
        for(Product p:productList)
        {
            System.out.println("    Product ID: "+p.getProductId() + "  Product Name: "+p.getName()+"   MRP: "+p.getMrp()+" Category: "+p.getCategory() +"  Stock: "+p.getStock());
        }
        System.out.println("=================================");
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
        validateProduct(product);
        productDAO.updateProduct(product);
    }
    public void validateProduct(Product product)
    {
        if(product.getName() == null || product.getName().isEmpty())
        {
            throw new InvalidProductException("Product name cannot be empty");
        }
        if(product.getDescription() == null || product.getDescription().isEmpty())
        {
            throw new InvalidProductException("Product description cannot be empty");
        }
        if(product.getMrp() <= 0)
        {
            throw  new InvalidProductException("MRP must be greater than 0");
        }
        if(product.getPrice() <=0)
        {
            throw new InvalidProductException("Price must be greater than 0");
        }
        if(product.getStock()<0)
        {
            throw  new InvalidProductException("Stock cannot be negative");
        }
        if(product.getCategory() == null || product.getCategory().isEmpty())
        {
            throw new InvalidProductException("Category cannot be negative");
        }
        if (product.getPrice() > product.getMrp()) {
            throw new IllegalArgumentException("Price cannot be greater than MRP");
        }
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
            throw new ProductNotFoundException("Unable to delete product with product id"+productId);
        }

        boolean deleted=productDAO.deleteProduct(productId);
        if(!deleted)
        {
            throw new ProductNotFoundException("Product Not found with product id   "+productId);
        }
    }

    public void searchProducts(String search)
    {
        if(search==null || search.isEmpty())
        {
            throw new ProductException("Search keyword cannot be empty");
        }
        List<Product> productList= productDAO.searchProducts(search);
        if(productList.isEmpty())
        {
            throw  new ProductNotFoundException("Product not found with keyword "+search);

        }
        for(Product p:productList)
        {
            System.out.println("Product Found");
            System.out.println("    ProductID:   "+p.getProductId() +"  Description   "+p.getDescription() +"  Name:  "+p.getName() + "  Price: "+p.getPrice() +"   MRP:    "+p.getMrp());
        }
    }

}
