package app;

import dao.*;
import model.Product;
import model.User;
import service.CartService;
import service.OrderService;
import service.ProductService;
import service.UserService;

import java.util.Scanner;

public class Main
{
    private static Scanner sc = new Scanner(System.in);
    static void main() {
        UserDAO userDAO = new UserDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        CartDAO cartDAO = new CartDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();

        UserService userService = new UserService(userDAO);
        ProductService productService = new ProductService(productDAO);
        CartService cartService = new CartService(cartDAO, cartItemDAO);
        OrderService orderService = new OrderService(cartDAO, cartItemDAO, orderDAO, orderItemDAO, productDAO);

        System.out.println("==== Welcome to RevShop ====");
        while (true) {
            System.out.println("New User? Press 1 to Register");
            System.out.println("Press 2 to Login");
            System.out.println("Press 3 to exit");
            System.out.println("Choose Option");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: register(userService);
                case 2:User user= login(userService);
                        if(user !=null)
                        {
                            if(user.getRole().equalsIgnoreCase("buyer"))
                            {
                                menuBuyer(user,productService,cartService,orderService);
                            }
                            else
                            {
                                sellerMenu(user,productService);
                            }
                        }
                        break;
                case 3:
                    System.out.println("==== Exiting ====");
                default:
                    System.out.println("Please Enter Correct Choice");
            }
        }
    }
         private static void register(UserService userService)
        {
            System.out.println("Enter your email");
            String email=sc.next();
            System.out.println("Enter your password");
            String password=sc.next();
            System.out.println("Enter your role (Buyer/Seller)");
            String role=sc.next().toUpperCase();
            boolean register= userService.register(email,password,role);
            if(register)
            {
                System.out.println("Registration done successfully");
                System.out.println("Welcome to RevShop family");
            }
            else {
                System.out.println("Registration failed");
            }
        }

        private static User login(UserService userService)
        {
            System.out.println("Enter your email to login");
            String email=sc.next();
            System.out.println("Enter your password to login");
            String password=sc.next();

            User user= userService.login(email,password);
            if(user == null)
            {
                System.out.println("Invalid Credentials");
            }
            else {
                System.out.println("Login Successfull");
            }

        return user;
        }
        private static void menuBuyer(User user,ProductService productService,CartService cartService, OrderService orderService)
        {
            while (true)
            {
                System.out.println("=== Buyer Menu ===");
                System.out.println("1. View Products");
                System.out.println("2. View Product by ID");
                System.out.println("3. Search Products by Keyword");
                System.out.println("4. Add to Cart");
                System.out.println("5. View Cart");
                System.out.println("6. Place Order");
                System.out.println("7. Logout");

                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                switch (choice)
                {
                    case 1:productService.viewAllProducts();
                    break;
                    case  2:
                        System.out.println("Enter Product Id");
                        int productid=sc.nextInt();
                        productService.viewProductById(productid);
                        break;
                    case 3:
                        System.out.println("Enter the keyword");
                        String keyword=sc.next();
                        productService.searchProducts(keyword);
                        break;
                    case 4:
                        System.out.println("Enter Product Id");
                        int productId=sc.nextInt();
                        System.out.println("Enter quantity");
                        int quantity=sc.nextInt();
                        cartService.addToCart(user.getUserId(),productId,quantity);
                        System.out.println("Item added to cart successfully! ");
                        break;
                    case 5:
                        cartService.viewCart(user.getUserId());
                        break;
                    case 6:
                        orderService.checkout(user.getUserId());
                        System.out.println("Order Successfully placed");
                        break;
                    case 7:
                        System.out.println("Logging out !!!!");
                        break;
                    default:
                        System.out.println("Please enter correct choice");
                }
            }

        }
        private static void sellerMenu(User user,ProductService productService)
        {
           while (true)
           {
               System.out.println("\n===== SELLER MENU =====");
               System.out.println("1. Add Product");
               System.out.println("2. View All Products");
               System.out.println("3. Update Product");
               System.out.println("4. Update Stock");
               System.out.println("5. Delete Product");
               System.out.println("6. Logout");

               System.out.print("Enter choice: ");
               int choice = sc.nextInt();
               switch (choice)
               {
                   case 1:
                       System.out.println("Enter product name");
                       String name =sc.next();
                       System.out.println("Enter product description");
                       String desc =sc.next();
                       System.out.println("Enter product price");
                       Double price =sc.nextDouble();
                       System.out.println("Enter MRP");
                       Double mrp= sc.nextDouble();
                       System.out.println("Enter category");
                       String category=sc.next();
                       System.out.println("Enter stock");
                       int stock=sc.nextInt();
                       productService.addProduct(name,desc,price,mrp,category,stock, user.getUserId());
                       break;
                   case 2:
                       productService.viewAllProducts();
                       break;
                   case 3:
                       System.out.println("Enter Product ID to update ");
                       int productId= sc.nextInt();

                       Product existing= productService.productDAO.getProductById(productId);
                       if (existing == null)
                       {
                           System.out.println("Product Not Found");
                           break;
                       }
                       else
                       {
                           System.out.println("Enter product name");
                           String newName =sc.next();
                           System.out.println("Enter product description");
                           String newDesc =sc.next();
                           System.out.println("Enter product price");
                           Double newPrice =sc.nextDouble();
                           System.out.println("Enter MRP");
                           Double new_mrp= sc.nextDouble();
                           System.out.println("Enter category");
                           String newCategory=sc.next();
                           System.out.println("Enter stock");
                           int newStock=sc.nextInt();
                           existing.setName(newName);
                           existing.setDescription(newDesc);
                           existing.setPrice(newPrice);
                           existing.setMrp(new_mrp);
                           existing.setCategory(newCategory);
                           existing.setStock(newStock);

                           productService.updateProduct(existing);
                           break;


                       }
                   case 4:
                       System.out.println("Enter Product Id to update stock");
                       int pid= sc.nextInt();
                       System.out.println("Enter new stock");
                       int stockValue=sc.nextInt();
                       productService.updateStock(pid,stockValue);
                       System.out.println("Stock Updated");
                       break;
                   case 5:
                       System.out.println("Enter product id to delete");
                       int deleteId=sc.nextInt();
                       productService.deleteProduct(deleteId);
                       return;
                   case 6:
                       System.out.println("Logging out !!");
                   default:
                       System.out.println("Please enter correct choice");
               }

           }

        }


    }

