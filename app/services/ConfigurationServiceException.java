package services;

public class ConfigurationServiceException extends Throwable {
    
    private static final long serialVersionUID = 8126486833558703221L;

    public ConfigurationServiceException() {
        super();
    }
    
    public ConfigurationServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public ConfigurationServiceException(String message) {
        super(message);
    }

    public ConfigurationServiceException(String message, String key) {
        super(message.replace("{}", key));
    }
    
}
