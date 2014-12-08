package models.exceptions;

public class ESDocumentNotFound extends Throwable {

    /**
     * 
     */
    private static final long serialVersionUID = -297807338728914527L;
    
    public ESDocumentNotFound() {
        super();
    }

    public ESDocumentNotFound(String message) {
        super(message);
    }

    public ESDocumentNotFound(Throwable throwable) {
        super(throwable);
    }
    
    public ESDocumentNotFound(String message, Throwable throwable) {
        super(message, throwable);
    }
}
