package service;

import dao.FavoriteDAO;
import exception.AlreadyMarkedFavoriteException;
import exception.FavoriteException;
import model.Product;

import java.util.List;

public class FavoriteService {
    private FavoriteDAO favoriteDAO;

    public FavoriteService(FavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    public void addToFavorites(int userId,int productId)
    {
        if(favoriteDAO.isFavorite(userId,productId))
        {
           throw new AlreadyMarkedFavoriteException("Product is already marked as favorites");
        }
        favoriteDAO.addFavorite(userId,productId);
    }

    public  void removeFromFavorites(int userId,int productId)
    {
        if (!favoriteDAO.isFavorite(userId, productId)) {
            throw new FavoriteException("Product not in favorites");
        }

        favoriteDAO.removeFavorite(userId, productId);
    }

    public List<Product> viewFavorites(int userId) {
        List<Product> favorites= favoriteDAO.getFavByUserId(userId);
        if(favorites.isEmpty() || favorites==null)
        {
            throw new FavoriteException("Favorite Product Not found");
        }
        return favorites;
    }

}
