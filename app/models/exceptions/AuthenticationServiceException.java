package models.exceptions;

public class AuthenticationServiceException extends Throwable {
    
    private static final long serialVersionUID = 1056716353202603438L;
    
    public AuthenticationServiceException() {
        super();
    }
    
    public AuthenticationServiceException(String message) {
        super(message);
    }
    
    public AuthenticationServiceException(Throwable throwable) {
        super(throwable);
    }
    
    public AuthenticationServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
