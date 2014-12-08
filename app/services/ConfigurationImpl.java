package services;

import play.Play;

import com.google.inject.Singleton;

@Singleton
public class ConfigurationImpl implements ConfigurationService {

    private static final String APPLICATION_HOST_KEY = "application.hostname";
    
    @Override
    public String getHostName() {
        return get(APPLICATION_HOST_KEY);
    }
    
    @Override
    public String get(String key) {
        return Play.application().configuration().getString(key);
    }
}
