package nehe.sharenews.utils;

public class UserNotFoundException  extends  Exception{
    
    private static final long serialVersionUID = 1L;
    
    public UserNotFoundException(String message) {
        super(message);
    }
}
