package app;

import dao.*;
import model.Order;
import model.Product;
import model.Review;
import model.User;
import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static Scanner sc = new Scanner(System.in);
    public static void main() {
        UserDAO userDAO = new UserDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        CartDAO cartDAO = new CartDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        ReviewDAO reviewDAO= new ReviewDAOImpl();
        FavoriteDAO favoriteDAO= new FavoriteDAOImpl();

        UserService userService = new UserService(userDAO);
        ProductService productService = new ProductService(productDAO);
        CartService cartService = new CartService(cartDAO, cartItemDAO);
        OrderService orderService = new OrderService(cartDAO, cartItemDAO, orderDAO, orderItemDAO, productDAO);
        ReviewService reviewService= new ReviewService(reviewDAO,orderDAO);
        FavoriteService favoriteService= new FavoriteService(favoriteDAO);


        System.out.println("==== Welcome to RevShop ====");
        while (true) {
            System.out.println("New User? Press 1 to Register");
            System.out.println("Press 2 to Login");
            System.out.println("Press 3 to Change Password");
            System.out.println("Press 4 for Forgot Password");
            System.out.println("Press 5 to exit");
            System.out.println("Choose Option");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: register(userService);
                break;
                case 2:User user= login(userService);
                        if(user !=null)
                        {
                            if(user.getRole().equalsIgnoreCase("buyer"))
                            {
                                menuBuyer(user,productService,cartService,orderService,reviewService,favoriteService);
                            }
                            else
                            {
                                sellerMenu(user,productService,orderDAO,reviewService);
                            }
                        }
                        break;
                case 3:
                    System.out.println("Enter your email");
                    String email=sc.next();
                    System.out.println("Enter your old password");
                    String oldPassword=sc.next();
                    System.out.println("Enter your new password");
                    String newPassword=sc.next();
                    boolean pass_change= userService.changePassword(email,oldPassword,newPassword);
                    if(pass_change)
                    {
                        System.out.println("Password changed successfully");
                    }
                    else {
                        System.out.println("Please enter correct old password");
                    }
                    break;
                case 4:
                    System.out.println("Enter your email");
                    String email1=sc.next();
                    String question = userService.getSecurityQuestion(email1);
                    if (question != null)
                    {
                        System.out.println(question);
                        System.out.println("Enter your answer");
                        String answer= sc.next();
                        boolean isCorrect= userService.verifySecurityAnswer(email1,answer);
                        if(isCorrect)
                        {
                            System.out.println("Enter new password");
                            String newPassword1=sc.next();
                            boolean reset= userService.resetPassword(email1,newPassword1);
                            if(reset)
                            {
                                System.out.println("Your password reset is successfull");
                            }
                            else
                            {
                                System.out.println("Reset Password failed");
                            }
                        }
                        else {
                            System.out.println("Incorrect Answer");
                        }


                    }
                    else {
                        System.out.println("Email not found");
                    }
                    break;

                case 5:
                    System.out.println("==== Exiting ====");
                    return;
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
            System.out.println("Enter security question");
            String securityQuestion=sc.next();
            System.out.println("Enter security answer");
            String securityAnswer=sc.next();
            boolean register= userService.register(email,password,role,securityQuestion,securityAnswer);
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
        private static void menuBuyer(User user,ProductService productService,CartService cartService, OrderService orderService,ReviewService reviewService ,FavoriteService favoriteService)
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
                System.out.println("7. View Order History");
                System.out.println("8. Remove item from Cart");
                System.out.println("9. Add a review ");
                System.out.println("10. Get Review by Product Id");
                System.out.println("11. Get  Average Rating by Product Id");
                System.out.println("12. Add product to favorites");
                System.out.println("13. Remove from favorites");
                System.out.println("14. View Favorites");
                System.out.println("15. Logout");

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
                        break;
                    case 7:
                        List<Order> orders= orderService.getUserOrders(user.getUserId());
                        if(orders.isEmpty())
                        {
                            System.out.println("No orders placed yet");
                        }
                        else {
                            System.out.println("**** Your Orders ****");
                            for(Order order:orders)
                            {
                                System.out.println(order);
                            }
                        }
                        break;
                    case 8:
                        System.out.println("Enter product id to remove");
                        int removeid= sc.nextInt();
                        cartService.removeFromCart(user.getUserId(),removeid);
                        break;
                    case 9:
                        System.out.println("Enter product id");
                        int pid=sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter rating between 1 to 5");
                        int rating=sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter comment");
                        String comment=sc.nextLine();
                        reviewService.addReview(user.getUserId(), pid,rating,comment);
                        break;
                    case 10:
                        System.out.println("Enter product id to view rating");
                        int p_rating=sc.nextInt();
                       List<Review > reviews= reviewService.getReviews(p_rating);
                       if (reviews.isEmpty())
                       {
                           System.out.println("Reviews not added yet ");
                       }
                       else
                       {
                           for(Review r:reviews)
                           {
                               System.out.println("==================");
//                               System.out.println("User Id  "+r.getUserId());
                               System.out.println("Rating  "+r.getRating());
                               System.out.println("Comment  "+r.getComment());
                               System.out.println("==================");

                           }
                       }
                        break;
                    case 11:
                        System.out.println("Enter product id to view  average rating");
                        int p_avg=sc.nextInt();
                        double avg =reviewService.getAverageRating(p_avg);
                        System.out.println("Average Rating with Project Id"+p_avg +"is "+avg);
                        break;
                    case 12:
                        System.out.println("Enter Product Id to add to favorites");
                        int fav_product= sc.nextInt();
                        favoriteService.addToFavorites(user.getUserId(), fav_product);
                        break;
                    case 13:
                        System.out.println("Enter Product Id to remove to favorites");
                        int rmv_product= sc.nextInt();
                        favoriteService.removeFromFavorites(user.getUserId(), rmv_product);
                        break;

                    case 14:
                        List<Product> favProducts=favoriteService.viewFavorites(user.getUserId());
                        if(favProducts.isEmpty())
                        {
                            System.out.println("No favorite items present");
                        }
                        else {
                            for(Product p: favProducts)
                            {
                                System.out.println("=========================");
                                System.out.println("Product Id  "+p.getProductId());
                                System.out.println("Name "+p.getName());
                                System.out.println("Price  "+p.getPrice());
                                System.out.println("=========================");

                            }
                        }

                        break;

                    case 15:
                        System.out.println("Logging out !!!!");
                      return;
                    default:
                        System.out.println("Please enter correct choice");
                }
            }

        }
        private static void sellerMenu(User user,ProductService productService,OrderDAO orderDAO,ReviewService reviewService)
        {
           while (true)
           {
               System.out.println("\n===== SELLER MENU =====");
               System.out.println("1. Add Product");
               System.out.println("2. View All Products");
               System.out.println("3. Update Product");
               System.out.println("4. Update Stock");
               System.out.println("5. Delete Product");
               System.out.println("6. View All Orders");
               System.out.println("7. Get Review by Product Id");
               System.out.println("8. Get  Average Rating by Product Id");
               System.out.println("9.Enter product id to check stock");
               System.out.println("10. Logout");
               System.out.print("Enter choice: ");
               int choice = sc.nextInt();
               switch (choice)
               {
                   case 1:
                       System.out.println("Enter product name");
                       String name =sc.next();
                       System.out.println("Enter product description");
                       sc.nextLine();
                       String desc =sc.next();
                       System.out.println("Enter product price");
                       while (!sc.hasNextDouble()) {
                           System.out.println("Invalid input! Enter number:");
                           sc.next();
                       }
                       double price =sc.nextDouble();
                       System.out.println("Enter MRP");
                       double mrp= sc.nextDouble();
                       System.out.println("Enter category");
                       String category=sc.next();
                       System.out.println("Enter stock");
                       int stock=sc.nextInt();
                       System.out.println("Enter the threshold stock");
                       int threshold_stock=sc.nextInt();
                       productService.addProduct(name,desc,price,mrp,category,stock, user.getUserId(),threshold_stock);
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
                           sc.nextLine();
                           String newDesc =sc.next();
                           System.out.println("Enter product price");
                           double newPrice =sc.nextDouble();
                           System.out.println("Enter MRP");
                           double new_mrp= sc.nextDouble();
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

                       break;
                   case 5:
                       System.out.println("Enter product id to delete");
                       int deleteId=sc.nextInt();
                       productService.deleteProduct(deleteId);
                       break;
                   case 6:

                       List<Order> allOrders = orderDAO.getAllOrders();
                       for (Order order : allOrders) {
                           System.out.println(order);
                       }
                       break;
                   case 7:
                       System.out.println("Enter product id to view rating");
                       int p_rating=sc.nextInt();
                       List<Review > reviews= reviewService.getReviews(p_rating);
                       if (reviews.isEmpty())
                       {
                           System.out.println("Reviews not added yet ");
                       }
                       else
                       {
                           for(Review r:reviews)
                           {
                               System.out.println("==================");
//                               System.out.println("User Id  "+r.getUserId());
                               System.out.println("Rating  "+r.getRating());
                               System.out.println("Comment  "+r.getComment());
                               System.out.println("==================");

                           }
                       }
                       break;
                   case 8:
                       System.out.println("Enter product id to view  average rating");
                       int p_avg=sc.nextInt();
                       double avg =reviewService.getAverageRating(p_avg);
                       System.out.println("Average Rating with Project Id"+p_avg +"is "+avg);
                       break;
                   case 9:
                       System.out.println("Enter product id to check stock:");
                       int s_id = sc.nextInt();
                       InventoryService inventoryService = new InventoryService();
                       inventoryService.checkStockByProductId(s_id);
                       break;

                   case 10:
                       System.out.println("Logging out !!");
                       return;
                   default:
                       System.out.println("Please enter correct choice");
               }

           }

        }


    }

