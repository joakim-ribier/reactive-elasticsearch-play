package services.indexation;

public class IndexationServiceException extends Throwable {
    
    private static final long serialVersionUID = 8126486833558703221L;

    public IndexationServiceException() {
        super();
    }
    
    public IndexationServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public IndexationServiceException(String message) {
        super(message);
    }

    public IndexationServiceException(String message, String key) {
        super(message.replace("{}", key));
    }
    
}
