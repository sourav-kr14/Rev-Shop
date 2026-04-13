import static org.junit.jupiter.api.Assertions.*;

import dao.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import model.Cart;
import model.CartItem;
import org.junit.jupiter.api.*;
import service.CartService;

public class CartServiceTest {

	private CartService cartService;
	private CartDAO cartDAO;
	private CartItemDAO cartItemDAO;
    private ProductDAO productDAO;

	@BeforeEach
	void setup() {
		cartDAO = new CartDAOImpl();
		cartItemDAO = new CartItemDAOImpl();
		cartService = new CartService(cartDAO, cartItemDAO,productDAO);
	}

	@BeforeAll
	static void beforeAllTests() {
		System.out.println("==== Starting Cart Service Test Suite ====");
	}

	@Test
	@Order(1)
	@DisplayName("Test to add new item in cart")
	void givenValidInput_ThenAddProductSuccess() {
		int userId = 5;
		int productId = 1;
		cartService.addToCart(userId, productId, 2);
		Cart cart = cartDAO.getCartByUserId(userId);
		assertNotNull(cart);
		List<CartItem> items = cartItemDAO.getCartItems(cart.getCartId());
		boolean found = items.stream().anyMatch(i -> i.getProductId() == productId);
		assertTrue(found);
	}

	@Test
	@Order(2)
	@DisplayName("Test to update new item in cart")
	void givenUpdatedQuantity_ThenUpdateProductSuccess() {
		int userId = 5;
		int productId = 1;

		cartService.addToCart(userId, productId, 1);
		cartService.addToCart(userId, productId, 2);

		Cart cart = cartDAO.getCartByUserId(userId);
		CartItem item = cartItemDAO.getCartItem(cart.getCartId(), productId);
		assertTrue(item.getQuantity() >= 3);
	}

	@Test
	@Order(3)
	@DisplayName("Test to remove new item from cart")
	void  givenValidInput_ThenRemoveProductSuccess() {
		int userId = 5;
		int productId = 2;

		cartService.addToCart(userId, productId, 1);
		cartService.removeFromCart(userId, productId);

		Cart cart = cartDAO.getCartByUserId(userId);
		CartItem item = cartItemDAO.getCartItem(cart.getCartId(), productId);

		assertNull(item);
	}

	@Test
	@Order(4)
	@DisplayName("Test to remove non existing element from cart")
	void givenInvalidInput_ThenRemoveProductFailure() {
		int userId = 9999;
		int productId = 1;

		assertDoesNotThrow(() -> {
			cartService.removeFromCart(userId, productId);
		});
	}

    @Test
    @Order(5)
    @DisplayName("Test to view item in cart")
    void givenValidInput_ThenViewCartSuccess() {
        int userId = 5;
        int productId = 1;

        cartService.addToCart(userId, productId, 2);

        List<CartItem> items = cartService.getCartItemsByUserId(userId);

        assertNotNull(items);
        assertFalse(items.isEmpty());

        assertTrue(
                items.stream()
                        .anyMatch(item -> item.getProductId() == productId
                                && item.getQuantity() == 2)
        );
    }

	@Test
	@Order(6)
	@DisplayName("Test to remove existing item in cart")
	void existingProduct_ThenRemoveProductSuccess() {
		int userId = 1;
		int productId = 1;
		int cartId;
		if (cartDAO.getCartByUserId(userId) == null) {
			cartId = cartDAO.createCart(userId);
		} else {
			cartId = cartDAO.getCartByUserId(userId).getCartId();
		}
		CartItem item = new CartItem(0, cartId, productId, 2);
		cartItemDAO.addCart(item);

		cartItemDAO.removeItem(cartId, productId);

		CartItem deleted = cartItemDAO.getCartItem(cartId, productId);

		assertNull(deleted);
	}

    @AfterAll
    static void afterallTests() {
        System.out.println("==== Finished Cart Service Test Suite ====");
    }
}
