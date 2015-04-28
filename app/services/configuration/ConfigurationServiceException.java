package services.configuration;

public class ConfigurationServiceException extends Throwable {
    
    private static final long serialVersionUID = 8126486833558703221L;
    
    public ConfigurationServiceException(String message, String key) {
        super(message.replace("{}", key));
    }
    
}
