package models.exceptions;

public class EncodeUtilsException extends Throwable {

    /**
     * 
     */
    private static final long serialVersionUID = 2979819444220078986L;

    public EncodeUtilsException() {
        super();
    }

    public EncodeUtilsException(String message) {
        super(message);
    }

    public EncodeUtilsException(Throwable throwable) {
        super(throwable);
    }

    public EncodeUtilsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
