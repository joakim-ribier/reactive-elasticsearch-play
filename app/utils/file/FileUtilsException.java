package utils.file;

public class FileUtilsException extends Throwable {
    
    private static final long serialVersionUID = -4118303621711236071L;
    
    public FileUtilsException() {
        super();
    }
    
    public FileUtilsException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public FileUtilsException(String message) {
        super(message);
    }
    
}
