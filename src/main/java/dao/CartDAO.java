package dao;

import model.Cart;

public interface CartDAO {
	int createCart(int userId);
	Cart getCartByUserId(int userId);

}
