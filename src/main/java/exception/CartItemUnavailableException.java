package exception;

public class CartItemUnavailableException extends  CartException{
    public CartItemUnavailableException(String message)
    {
        super(message);
    }
}
