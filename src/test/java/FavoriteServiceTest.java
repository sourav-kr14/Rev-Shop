import dao.FavoriteDAO;
import dao.FavoriteDAOImpl;
import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


    @Test
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
    void testViewFavorites() {
        int userId = 1;

        List<Product> favorites = favoriteService.viewFavorites(userId);

        assertNotNull(favorites);
    }
}