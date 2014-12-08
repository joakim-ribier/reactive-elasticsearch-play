package services;

import com.google.inject.Singleton;

@Singleton
public class ConfigurationImplTest implements ConfigurationService {

    @Override
    public String get(String key) {
        return key;
    }

    @Override
    public String getHostName() {
        // TODO Auto-generated method stub
        return null;
    }
}
