package app;

import dao.*;
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
            }

        }

    }

