import dao.CartDAO;
import dao.CartDAOImpl;
import dao.CartItemDAO;
import dao.CartItemDAOImpl;
import model.Cart;
import model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CartService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;
    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;

    @BeforeEach
    void setup() {
        cartDAO = new CartDAOImpl();
        cartItemDAO = new CartItemDAOImpl();

        cartService = new CartService(cartDAO, cartItemDAO);
    }


    @Test
    void testAddToCartNewItem() {
        int userId = 5;
        int productId = 1;

        cartService.addToCart(userId, productId, 2);

        Cart cart = cartDAO.getCartByUserId(userId);
        assertNotNull(cart);

        List<CartItem> items = cartItemDAO.getCartItems(cart.getCartId());

        boolean found = items.stream()
                .anyMatch(i -> i.getProductId() == productId);

        assertTrue(found);
    }


    @Test
    void testAddToCartUpdateQuantity() {
        int userId = 5;
        int productId = 1;

        cartService.addToCart(userId, productId, 1);
        cartService.addToCart(userId, productId, 2);

        Cart cart = cartDAO.getCartByUserId(userId);
        CartItem item = cartItemDAO.getCartItem(cart.getCartId(), productId);

        assertTrue(item.getQuantity() >= 3);
    }


    @Test
    void testRemoveFromCart() {
        int userId = 5;
        int productId = 2;

        cartService.addToCart(userId, productId, 1);
        cartService.removeFromCart(userId, productId);

        Cart cart = cartDAO.getCartByUserId(userId);
        CartItem item = cartItemDAO.getCartItem(cart.getCartId(), productId);

        assertNull(item);
    }


    @Test
    void testRemoveFromNonExistingCart() {
        int userId = 9999;
        int productId = 1;

        assertDoesNotThrow(() -> {
            cartService.removeFromCart(userId, productId);
        });
    }


    @Test
    void testViewCartWithItems() {
        int userId = 5;
        int productId = 1;

        cartService.addToCart(userId, productId, 2);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        cartService.viewCart(userId);

        String output = out.toString();

        assertTrue(output.contains("Cart Items"));
        assertTrue(output.contains("Product id"));
    }


    @Test
    void testRemoveExistingItem() {
        int userId = 1;
        int productId = 1;

        // Ensure cart exists
        int cartId;
        if (cartDAO.getCartByUserId(userId) == null) {
            cartId = cartDAO.createCart(userId);
        } else {
            cartId = cartDAO.getCartByUserId(userId).getCartId();
        }

        // Add item first
        CartItem item = new CartItem(0, cartId, productId, 2);
        cartItemDAO.addCart(item);

        // Remove item
        cartItemDAO.removeItem(cartId, productId);

        // Verify deletion
        CartItem deleted = cartItemDAO.getCartItem(cartId, productId);

        assertNull(deleted);
    }
}