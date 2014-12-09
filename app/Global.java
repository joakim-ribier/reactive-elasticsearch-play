import play.Application;
import play.GlobalSettings;
import services.AuthenticationImpl;
import services.AuthenticationService;
import services.ConfigurationImpl;
import services.ConfigurationService;
import services.ESSearchImpl;
import services.ESSearchService;
import services.ESConstantService;
import services.ESConstantImpl;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Global extends GlobalSettings {
    
    private Injector injector;

    @Override
    public void onStart(Application application) {
        this.injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(IESServerEmbedded.class).to(ESServerEmbedded.class);
                bind(IEncodeUtils.class).to(EncodeUtils.class);
                
                bind(ESSearchService.class).to(ESSearchImpl.class);
                bind(ESConstantService.class).to(ESConstantImpl.class);
                bind(ConfigurationService.class).to(ConfigurationImpl.class);
                bind(AuthenticationService.class).to(AuthenticationImpl.class);
            }
        });
        
        injector.getInstance(ESServerEmbedded.class).init();
    }
    
    @Override
    public void onStop(Application arg0) {
        injector.getInstance(ESServerEmbedded.class).stop();
    }

    @Override
    public <T> T getControllerInstance(Class<T> aClass) throws Exception {
        return injector.getInstance(aClass);
    }
}
