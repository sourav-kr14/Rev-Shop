import dao.FavoriteDAO;
import dao.FavoriteDAOImpl;
import dao.ProductDAO;
import model.Product;
import org.junit.jupiter.api.*;
import service.FavoriteService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FavoriteServiceTest {
    private FavoriteService favoriteService;
    private FavoriteDAO favoriteDAO;
    private ProductDAO productDAO;

    @BeforeEach
    void setup() {
        favoriteDAO = new FavoriteDAOImpl();
        favoriteService = new FavoriteService(favoriteDAO,productDAO);
    }

    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Favourite Service Test Suite ====");
    }

    @Test
    @Order(1)
    @DisplayName("Test to add favorites")
    void givenValidInfo_WhenUserAddFavorites_ThenReturnSuccess() {
        int userId = 4;
        int productId = 4;

        favoriteService.addToFavorites(userId, productId);

        List<Product> favorites = favoriteService.viewFavorites(userId);

        boolean found = favorites.stream()
                .anyMatch(p -> p.getProductId() == productId);

        assertTrue(found);
    }


    @Test
    @Order(2)
    @DisplayName("Test to add duplicate favorites")
    void givenDuplicateInfo_WhenUserAddFavorites_ThenReturnFailure() {
        int userId = 4;
        int productId = 4;
        favoriteService.addToFavorites(userId, productId);
        favoriteService.addToFavorites(userId, productId);
        List<Product> favorites = favoriteService.viewFavorites(userId);

        long count = favorites.stream()
                .filter(p -> p.getProductId() == productId)
                .count();
        assertTrue(count >= 1);
    }


    @Test
    @Order(3)
    @DisplayName("Test to remove from favorites")
    void givenValidInput_ThenRemoveFavorites() {
        int userId = 1;
        int productId = 1;
        favoriteService.addToFavorites(userId, productId);
        favoriteService.removeFromFavorites(userId, productId);
        List<Product> favorites = favoriteService.viewFavorites(userId);
        boolean found = favorites.stream()
                .anyMatch(p -> p.getProductId() == productId);
        assertFalse(found);
    }

    @Test
    @Order(4)
    @DisplayName("Test to view favorites")
    void givenValidInput_ThenGetReviewsSuccess() {
        int userId = 1;
        List<Product> favorites = favoriteService.viewFavorites(userId);
        assertNotNull(favorites);
    }


    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Favourite Service Test Suite ====");

    }
}