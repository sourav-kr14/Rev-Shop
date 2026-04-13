package exception;

public class InvalidRatingException extends ReviewException{
    public InvalidRatingException(String message)
    {
        super(message);
    }
}
