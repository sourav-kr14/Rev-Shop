package exception;

public class AlreadyMarkedFavoriteException extends  FavoriteException{
    public AlreadyMarkedFavoriteException(String message)
    {
        super(message);
    }
}
