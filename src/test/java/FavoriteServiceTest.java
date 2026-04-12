import dao.FavoriteDAO;
import dao.FavoriteDAOImpl;
import model.Product;
import org.junit.jupiter.api.*;
import service.FavoriteService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FavoriteServiceTest {
    private FavoriteService favoriteService;
    private FavoriteDAO favoriteDAO;

    @BeforeEach
    void setup() {
        favoriteDAO = new FavoriteDAOImpl();
        favoriteService = new FavoriteService(favoriteDAO);
    }

    @BeforeAll
    static void beforeAllTests()
    {
        System.out.println("==== Starting Favourite Service Test Suite ====");
    }

    @AfterAll
    static void afterallTests()
    {
        System.out.println("==== Finished Favourite Service Test Suite ====");

    }



    @Test
    @Order(1)
    @DisplayName("Test to add favorites")
    void testAddToFavorites() {
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
    void testAddDuplicateFavorite() {
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
    void testRemoveFromFavorites() {
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
    void testViewFavorites() {
        int userId = 1;
        List<Product> favorites = favoriteService.viewFavorites(userId);
        assertNotNull(favorites);
    }
}