package exception;

public class CartEmptyException extends  OrderException {
    public CartEmptyException (String message)
    {
        super (message);
    }
}
