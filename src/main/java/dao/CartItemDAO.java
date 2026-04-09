package dao;

import model.CartItem;
import java.util.*;
public interface CartItemDAO
{
    void addCart(CartItem item);
    void updateQuantity(int cartId,int productId, int quantity);
//    void removeItems(int cartId,int productId);
    List <CartItem> getCartItems(int cartId);
    void emptyCart(int cartId);
    CartItem getCartItem(int cartId,int productId);
    void removeItem(int userId,int productId);


}
