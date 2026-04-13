package service;

import dao.CartDAO;
import dao.CartItemDAO;
import exception.CartEmptyException;
import exception.CartItemUnavailableException;
import exception.InvalidQuantityException;
import model.Cart;
import model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartService {

	private CartDAO cartDAO;
	private CartItemDAO cartItemDAO;

	public CartService(CartDAO cartDAO, CartItemDAO cartItemDAO) {
		this.cartDAO = cartDAO;
		this.cartItemDAO = cartItemDAO;
	}

	public void addToCart(int userId, int productId, int quantity) {
        if(quantity<=0)
        {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }
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

	public List<CartItem> viewCart(int userId) {
		Cart cart = cartDAO.getCartByUserId(userId);
		if (cart == null) {
			throw new CartEmptyException("Cart is empty");
		}

        List<CartItem> items= cartItemDAO.getCartItems(cart.getCartId());
        if(items.isEmpty())
        {
            throw new CartEmptyException("Cart is empty");
        }
        return items;
//        System.out.println("==== Cart Items ====");
//        for(CartItem item:items)
//        {
//            System.out.println("Product id: "+item.getProductId()  + "====" + "Quantity:    "+item.getQuantity());
//        }
	}


    public List<CartItem> getCartItemsByUserId(int userId) {
        Cart cart = cartDAO.getCartByUserId(userId);
        if (cart == null) {
           throw new CartEmptyException("Cart is empty");
        }
        List<CartItem> items= cartItemDAO.getCartItems(cart.getCartId());
        if(items.isEmpty())
        {
            throw new CartEmptyException("Cart is empty");
        }
        return items;
    }

    public void removeFromCart(int userId,int productId)
    {
        Cart cart= cartDAO.getCartByUserId(userId);
        if(cart==null)
        {
           throw new CartItemUnavailableException("Item not found in cart with user id"+userId +"and" +"product id"+productId);
        }
        CartItem cartItem = cartItemDAO.getCartItem(cart.getCartId(), productId);
        if(cartItem == null)
        {
            throw new CartItemUnavailableException("Item not found in cart with user id"+userId +"and" +"product id"+productId);
        }
        cartItemDAO.removeItem(cart.getCartId(),productId);

    }





}
