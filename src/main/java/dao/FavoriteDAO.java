package dao;

import model.Product;

import java.util.List;

public interface FavoriteDAO {
    void addFavorite(int userId, int productId);
    void removeFavorite(int userId,int productId);
    List<Product> getFavByUserId(int userId);
    boolean isFavorite(int userId,int productId);
}
