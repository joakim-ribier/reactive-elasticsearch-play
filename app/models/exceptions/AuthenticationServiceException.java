package models.exceptions;

public class AuthenticationServiceException extends Throwable {
    
    private static final long serialVersionUID = 1056716353202603438L;
    
    public AuthenticationServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
