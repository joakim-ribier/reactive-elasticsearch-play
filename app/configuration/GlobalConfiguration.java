package configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import play.Application;
import play.GlobalSettings;
import services.AuthenticationImpl;
import services.AuthenticationService;
import services.ConfigurationImpl;
import services.ConfigurationService;
import services.ESConstantImpl;
import services.ESConstantService;
import services.ESSearchImpl;
import services.ESSearchService;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;

public abstract class GlobalConfiguration extends GlobalSettings {
	
	protected Injector injector;

	protected abstract AbstractModule buildAbstractModule();
	
    @Override
    public void onStart(Application application) {
        this.injector = Guice.createInjector(buildAbstractModule());
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
