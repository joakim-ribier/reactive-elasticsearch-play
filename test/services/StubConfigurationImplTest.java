package services;

import com.google.inject.Singleton;

@Singleton
public class StubConfigurationImplTest implements ConfigurationService {

    @Override
    public String get(String key) {
    	switch (key) {
		case "application.security.login":
			return "admin";
		default:
			return key;
		}
    }

    @Override
    public String getHostName() {
        return null;
    }
}
