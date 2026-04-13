package exception;

public class EmptyCartException extends CartException{
    public EmptyCartException(String message)
    {
        super(message);
    }
}
