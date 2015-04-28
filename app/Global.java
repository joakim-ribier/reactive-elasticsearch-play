import services.AuthenticationImpl;
import services.AuthenticationService;
import services.configuration.ConfigurationImpl;
import services.configuration.ConfigurationService;

import com.google.inject.AbstractModule;

import configuration.GlobalConfiguration;

public class Global extends GlobalConfiguration {
    
    @Override
    protected AbstractModule myModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(ConfigurationService.class).to(ConfigurationImpl.class);
                bind(AuthenticationService.class).to(AuthenticationImpl.class);
            }
        };
    }
    
}
