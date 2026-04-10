package service;

import dao.FavoriteDAO;
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
            System.out.println("Already in favorites");
        }
        favoriteDAO.addFavorite(userId,productId);
    }

    public  void removeFromFavorites(int userId,int productId)
    {
        favoriteDAO.removeFavorite(userId,productId);
    }
    public List<Product> viewFavorites(int userId) {
        return favoriteDAO.getFavByUserId(userId);
    }

}
