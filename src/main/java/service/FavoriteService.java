package service;

import dao.FavoriteDAO;
import dao.ProductDAO;
import dao.UserDAO;
import exception.AlreadyMarkedFavoriteException;
import exception.FavoriteException;
import exception.ProductNotFoundException;
import model.Product;
import model.User;

import java.util.List;

public class FavoriteService {
    private FavoriteDAO favoriteDAO;
    private ProductDAO productDAO;


    public FavoriteService(FavoriteDAO favoriteDAO,ProductDAO productDAO) {
        this.favoriteDAO = favoriteDAO;
        this.productDAO=productDAO;
    }

    public void addToFavorites(int userId,int productId)
    {
        Product product= productDAO.getProductById(productId);
        if(product == null)
        {
            throw new ProductNotFoundException("Product not found product id"+productId);
        }



        if(favoriteDAO.isFavorite(userId,productId))
        {
           throw new AlreadyMarkedFavoriteException("Product is already marked as favorites");
        }
        favoriteDAO.addFavorite(userId,productId);
    }

    public  void removeFromFavorites(int userId,int productId)
    {
        Product product= productDAO.getProductById(productId);
        if(product == null)
        {
            throw new ProductNotFoundException("Product not found product id"+productId);
        }
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
