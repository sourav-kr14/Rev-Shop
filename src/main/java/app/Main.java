package app;

import dao.*;
import exception.ProductNotFoundException;
import exception.UserException;
import model.Order;
import model.Product;
import model.Review;
import model.User;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main() {
        UserDAO userDAO = new UserDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        CartDAO cartDAO = new CartDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        ReviewDAO reviewDAO = new ReviewDAOImpl();
        FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

        UserService userService = new UserService(userDAO);
        ProductService productService = new ProductService(productDAO);
        CartService cartService = new CartService(cartDAO, cartItemDAO,productDAO);
        OrderService orderService = new OrderService(cartDAO, cartItemDAO, orderDAO, orderItemDAO, productDAO);
        ReviewService reviewService = new ReviewService(reviewDAO, orderDAO, productDAO);
        FavoriteService favoriteService = new FavoriteService(favoriteDAO,productDAO);
        InventoryService inventoryService= new InventoryService();


        System.out.println("==== Welcome to RevShop ====");
        while (true) {
            try {
                System.out.println("New User? Press 1 to Register");
                System.out.println("Press 2 to Login");
                System.out.println("Press 3 to Change Password");
                System.out.println("Press 4 for Forgot Password");
                System.out.println("Press 5 to exit");
                System.out.println("Choose Option");
                System.out.println("===========================");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        register(userService);
                        break;
                    case 2:
                        User user = login(userService);
                        if (user != null) {
                            if (user.getRole().equalsIgnoreCase("buyer")) {
                                menuBuyer(user, productService, cartService, orderService, reviewService, favoriteService);
                            } else {
                                sellerMenu(user, productService, orderDAO, reviewService);
                            }
                        }
                        break;
                    case 3:
                        changePassword(userService);
                        break;
                    case 4:
                        getSecurityQuestion(userService);
                        break;

                    case 5:
                        System.out.println("==== Exiting ====");
                        return;
                    default:
                        System.out.println("Please Enter Correct Choice");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }
    }

    private static void register(UserService userService) {
        try {
            System.out.println("Enter your email");
            String email = sc.next();
            System.out.println("Enter your password");
            String password = sc.next();
            System.out.println("Enter your role (Buyer/Seller)");
            String role = sc.next().toUpperCase();
            System.out.println("Enter security question");
            String securityQuestion = sc.next();
            System.out.println("Enter security answer");
            String securityAnswer = sc.next();
            userService.register(email, password, role, securityQuestion, securityAnswer);
        } catch (UserException e) {
            System.out.println("Error while registering the user:" + e.getMessage());
        }
    }

    private static User login(UserService userService) {
        try {
            System.out.println("Enter your email to login");
            String email = sc.next();
            System.out.println("Enter your password to login");
            String password = sc.next();

            User user = userService.login(email, password);
            System.out.println("Login Successfull");
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    private static void menuBuyer(User user, ProductService productService, CartService cartService, OrderService orderService, ReviewService reviewService, FavoriteService favoriteService) {
        try {
            while (true) {
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
                switch (choice) {
                    case 1:
                        viewAllProducts(productService);
                        break;
                    case 2:
                        viewProductById(productService);
                        break;
                    case 3:
                        searchByKeyword(productService);
                        break;
                    case 4:
                        addToCart(cartService, user);
                        break;
                    case 5:
                        viewCart(cartService, user);
                        break;

                    case 6:
                        placeOrder(orderService, user);
                        break;
                    case 7:
                        viewOrderHistory(orderService, user);
                        break;
                    case 8:
                        removeItemFromCart(cartService, user);
                        break;
                    case 9:
                        addReview(reviewService, user);
                        break;
                    case 10:
                        viewRatingByProductId(reviewService);
                        break;
                    case 11:
                        getAverageRating(reviewService);
                        break;
                    case 12:
                        addToFavorites(favoriteService, user);
                        break;
                    case 13:
                        removeFromFavorites(favoriteService, user);
                        break;
                    case 14:
                        viewFavorites(favoriteService, user);
                        break;

                    case 15:
                        System.out.println("Logging out !!!!");
                        return;
                    default:
                        System.out.println("Please enter correct choice");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }

    }

    private static void sellerMenu(User user, ProductService productService, OrderDAO orderDAO, ReviewService reviewService) {
        try {
            while (true) {
                System.out.println("\n===== SELLER MENU =====");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Update Stock");
                System.out.println("5. Delete Product");
                System.out.println("6. View All Orders");
                System.out.println("7. Get Review by Product Id");
                System.out.println("8. Get  Average Rating by Product Id");
                System.out.println("9. Enter product id to check stock");
                System.out.println("10. Logout");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        addProduct(productService, user);
                        break;
                    case 2:
                        viewAllProducts(productService);
                        break;
                    case 3:
                        updateByProductID(productService);
                        break;
                    case 4:
                        updateStock(productService);
                        break;
                    case 5:
                        deleteProductById(productService);
                        break;
                    case 6:
                        viewAllOrders(orderDAO);
                        break;
                    case 7:
                        viewRating(reviewService);
                        break;
                    case 8:
                        getAverageRating(reviewService);
                        break;
                    case 9:
                        checkStockById(new InventoryService());
                        break;

                    case 10:
                        System.out.println("Logging out !!");
                        return;
                    default:
                        System.out.println("Please enter correct choice");
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }

    }

    private static void changePassword(UserService userService) {
        try {
            System.out.println("Enter your email");
            String email = sc.next();
            System.out.println("Enter your old password");
            String oldPassword = sc.next();
            System.out.println("Enter your new password");
            String newPassword = sc.next();
            boolean pass_change = userService.changePassword(email, oldPassword, newPassword);
            if (pass_change) {
                System.out.println("Password changed successfully");
            } else {
                System.out.println("Please enter correct old password");
            }

        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getSecurityQuestion(UserService userService) {
        try {
            System.out.println("Enter your email");
            String email1 = sc.next();
            String question = userService.getSecurityQuestion(email1);
            if (question != null) {
                System.out.println(question);
                System.out.println("Enter your answer");
                String answer = sc.next();
                boolean isCorrect = userService.verifySecurityAnswer(email1, answer);
                if (isCorrect) {
                    System.out.println("Enter new password");
                    String newPassword1 = sc.next();
                    boolean reset = userService.resetPassword(email1, newPassword1);
                    if (reset) {
                        System.out.println("Your password reset is successfull");
                    } else {
                        System.out.println("Reset Password failed");
                    }
                } else {
                    System.out.println("Incorrect Answer");
                }


            } else {
                System.out.println("Email not found");
            }
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void viewAllProducts(ProductService productService) {
        productService.viewAllProducts();
    }

    private static void viewProductById(ProductService productService) {
        System.out.println("Enter Product Id");
        int productid = sc.nextInt();
        productService.viewProductById(productid);
    }

    private static void searchByKeyword(ProductService productService) {
        System.out.println("Enter the keyword");
        sc.nextLine();
        String keyword = sc.nextLine();
        productService.searchProducts(keyword);
    }

    private static void addToCart(CartService cartService, User user) {
        System.out.println("Enter Product Id");
        int productId = sc.nextInt();
        System.out.println("Enter quantity");
        int quantity = sc.nextInt();
        cartService.addToCart(user.getUserId(), productId, quantity);
        System.out.println("Item added to cart successfully! ");
    }


    private static void viewCart(CartService cartService, User user) {
        cartService.viewCart(user.getUserId());
    }

    private static void placeOrder(OrderService orderService, User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter delivery address");
        String address=sc.nextLine();
        System.out.println("Enter payment method (COD,CARD,UPI)");
        String paymentMethod=sc.nextLine();
        orderService.checkout(user.getUserId(),address,paymentMethod);
    }

    private static void viewOrderHistory(OrderService orderService, User user) {
        List<Order> orders = orderService.getUserOrders(user.getUserId());
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet");
        } else {
            System.out.println("**** Your Orders ****");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private static void removeItemFromCart(CartService cartService, User user) {
        System.out.println("Enter product id to remove");
        int removeid = sc.nextInt();
        cartService.removeFromCart(user.getUserId(), removeid);
    }

    private static void addReview(ReviewService reviewService, User user) {
        System.out.println("Enter product id");
        int pid = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter rating between 1 to 5");
        int rating = sc.nextInt();
        System.out.println("Enter comment");
        sc.nextLine();
        String comment = sc.nextLine();
        reviewService.addReview(user.getUserId(), pid, rating, comment);
    }

    private static void viewRatingByProductId(ReviewService reviewService) {
        System.out.println("Enter product id to view rating");
        int p_rating = sc.nextInt();
        List<Review> reviews = reviewService.getReviews(p_rating);
        if (reviews.isEmpty()) {
            System.out.println("Reviews not added yet ");
        } else {
            for (Review r : reviews) {
                System.out.println("==================");
//                               System.out.println("User Id  "+r.getUserId());
                System.out.println("Rating  " + r.getRating());
                System.out.println("Comment  " + r.getComment());
                System.out.println("==================");

            }
        }
    }

    private static void getAverageRating(ReviewService reviewService) {
        System.out.println("Enter product id to view  average rating");
        int p_avg = sc.nextInt();
        double avg = reviewService.getAverageRating(p_avg);
        System.out.println("Average Rating with Project Id  " + p_avg + "   is " + avg);
    }

    private static void addToFavorites(FavoriteService favoriteService, User user) {
        System.out.println("Enter Product Id to add to favorites");
        int fav_product = sc.nextInt();
        favoriteService.addToFavorites(user.getUserId(), fav_product);
    }

    private static void removeFromFavorites(FavoriteService favoriteService, User user) {
        System.out.println("Enter Product Id to remove to favorites");
        int rmv_product = sc.nextInt();
        favoriteService.removeFromFavorites(user.getUserId(), rmv_product);
    }

    private static void viewFavorites(FavoriteService favoriteService, User user) {
        List<Product> favProducts = favoriteService.viewFavorites(user.getUserId());
        if (favProducts.isEmpty()) {
            System.out.println("No favorite items present");
        } else {
            for (Product p : favProducts) {
                System.out.println("=========================");
                System.out.println("Product Id  " + p.getProductId());
                System.out.println("Name " + p.getName());
                System.out.println("Price  " + p.getPrice());
                System.out.println("=========================");

            }
        }
    }


    private static void addProduct(ProductService productService, User user) {
        System.out.println("Enter product name");
        String name = sc.next();
        System.out.println("Enter product description");
        sc.nextLine();
        String desc = sc.nextLine();
        System.out.println("Enter product price");
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input! Enter number:");
            sc.next();
        }
        double price = sc.nextDouble();
        System.out.println("Enter MRP");
        double mrp = sc.nextDouble();
        System.out.println("Enter category");
        String category = sc.next();
        System.out.println("Enter stock");
        int stock = sc.nextInt();
        System.out.println("Enter the threshold stock");
        int threshold_stock = sc.nextInt();
        productService.addProduct(name, desc, price, mrp, category, stock, user.getUserId(), threshold_stock);
    }

    private static void updateByProductID(ProductService productService) {
        System.out.println("Enter Product ID to update ");
        int productId = sc.nextInt();
        Product existing = productService.productDAO.getProductById(productId);
        if (existing == null) {
            System.out.println("Product Not Found");

        } else {
            System.out.println("Enter product name");
            String newName = sc.next();
            System.out.println("Enter product description");
            sc.nextLine();
            String newDesc = sc.nextLine();
            System.out.println("Enter product price");
            double newPrice = sc.nextDouble();
            System.out.println("Enter MRP");
            double new_mrp = sc.nextDouble();
            System.out.println("Enter category");
            String newCategory = sc.next();
            System.out.println("Enter stock");
            int newStock = sc.nextInt();
            existing.setName(newName);
            existing.setDescription(newDesc);
            existing.setPrice(newPrice);
            existing.setMrp(new_mrp);
            existing.setCategory(newCategory);
            existing.setStock(newStock);
            productService.updateProduct(existing);
        }
    }

    private static void updateStock(ProductService productService)
    {
        System.out.println("Enter Product Id to update stock");
        int pid = sc.nextInt();
        System.out.println("Enter new stock");
        int stockValue = sc.nextInt();
        productService.updateStock(pid, stockValue);
    }

    private static void deleteProductById(ProductService productService)
    {
        System.out.println("Enter product id to delete");
        int deleteId = sc.nextInt();
        productService.deleteProduct(deleteId);
    }
    private static void  viewAllOrders(OrderDAO orderDAO)
    {
        List<Order> allOrders = orderDAO.getAllOrders();
        System.out.println(" ==== Order Details ====");
        for (Order order : allOrders) {
            System.out.println(order);
        }
        System.out.println("========================");
    }
    private static void viewRating(ReviewService reviewService)
    {
        System.out.println("Enter product id to view rating");
        int p_rating = sc.nextInt();
        try {
            List<Review> reviews = reviewService.getReviews(p_rating);
            if (reviews.isEmpty()) {
                System.out.println("No reviews added yet");
            } else {
                for (Review r : reviews) {
                    System.out.println("==================");
                    System.out.println("Rating  " + r.getRating());
                    System.out.println("Comment  " + r.getComment());
                    System.out.println("==================");
                }
            }

        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void checkStockById(InventoryService inventoryService)
    {
        System.out.println("Enter product id to check stock:");
        int s_id = sc.nextInt();
        inventoryService.checkStockByProductId(s_id);
    }
}

