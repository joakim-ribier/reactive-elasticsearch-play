package services.search;

public class SearchServiceException extends Throwable {
    
    private static final long serialVersionUID = -650861050260621072L;
    
    public SearchServiceException() {
        super();
    }
    
    public SearchServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
