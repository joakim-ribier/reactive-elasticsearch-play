package services.i18n;

public class I18nException extends Throwable {

    private static final long serialVersionUID = 7895014017500207223L;
    
    public I18nException() {
        super();
    }
    
    public I18nException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
