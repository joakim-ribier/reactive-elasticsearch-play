package models.exceptions;

public class ESDocumentFieldNotFound extends Throwable {

    /**
     * 
     */
    private static final long serialVersionUID = -297807338728914527L;

    public ESDocumentFieldNotFound() {
        super();
    }

    public ESDocumentFieldNotFound(String message) {
        super(message);
    }

    public ESDocumentFieldNotFound(Throwable throwable) {
        super(throwable);
    }

    public ESDocumentFieldNotFound(String message, Throwable throwable) {
        super(message, throwable);
    }
}
