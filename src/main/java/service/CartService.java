package service;

import dao.CartDAO;
import dao.CartItemDAO;
import model.Cart;
import model.CartItem;

import java.util.List;

public class CartService {

	private CartDAO cartDAO;
	private CartItemDAO cartItemDAO;

	public CartService(CartDAO cartDAO, CartItemDAO cartItemDAO) {
		this.cartDAO = cartDAO;
		this.cartItemDAO = cartItemDAO;
	}

	public void addToCart(int userId, int productId, int quantity) {
		Cart cart = cartDAO.getCartByUserId(userId);
		if (cart == null) {
			int cartId = cartDAO.createCart(userId);
			cart = new Cart(cartId, userId);
		}

		CartItem existing = cartItemDAO.getCartItem(cart.getCartId(), productId);
		if (existing != null) {
			int newQuantity = existing.getQuantity() + quantity;
			cartItemDAO.updateQuantity(cart.getCartId(), productId, newQuantity);
		} else {
			CartItem item = new CartItem(0, cart.getCartId(), productId, quantity);
			cartItemDAO.addCart(item);
		}
	}

	public void viewCart(int userId) {
		Cart cart = cartDAO.getCartByUserId(userId);
		if (cart == null) {
			System.out.println("Cart in Empty!!! ");
            return;
		}

        List<CartItem> items= cartItemDAO.getCartItems(cart.getCartId());
        if(items.isEmpty())
        {
            System.out.println("Cart is empty");
            return;
        }
        System.out.println("==== Cart Items ====");
        for(CartItem item:items)
        {
            System.out.println("Product id: "+item.getProductId()  + "====" + "Quantity:    "+item.getQuantity());

        }


	}

    public void removeFromCart(int userId,int productId)
    {
        Cart cart = cartDAO.getCartByUserId(userId);
        if(cart == null)
        {
            System.out.println("Cart is empty");
            return;
        }
        cartItemDAO.removeItems(cart.getCartId(),productId);
        System.out.println("Items removed from cart");
    }

    public void removeFromCart(int userId)
    {
        Cart cart = cartDAO.getCartByUserId(userId);
        if(cart == null)
        {
            System.out.println("Cart doesn't  exist");
            return;
        }
        cartItemDAO.emptyCart(cart.getCartId());
        System.out.println("Cart emptied successfully");
    }



}
