package guice;

import play.Application;
import services.AuthenticationImpl;
import services.AuthenticationService;
import services.ConfigurationImplTest;
import services.ConfigurationService;
import services.ESConstantImpl;
import services.ESConstantService;
import services.ESSearchImpl;
import services.ESSearchService;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

import configuration.GlobalConfiguration;
import controllers.AuthenticationController;

public class GlobalTest extends GlobalConfiguration {
	
	@Override
    protected AbstractModule buildAbstractModule() {
		return new AbstractModule() {
            @Override
            protected void configure() {
                bind(IESServerEmbedded.class).to(ESServerEmbedded.class);
                bind(IEncodeUtils.class).to(EncodeUtils.class);
                
                bind(ESSearchService.class).to(ESSearchImpl.class);
                bind(ESConstantService.class).to(ESConstantImpl.class);
                bind(ConfigurationService.class).to(ConfigurationImplTest.class);
                bind(AuthenticationService.class).to(AuthenticationImpl.class);
                
                bind(AuthenticationController.class).asEagerSingleton();
            }
        };
    }
	
	@Override
    public void onStart(Application application) {
        this.injector = Guice.createInjector(buildAbstractModule());
    }
	
	@Override
	public void onStop(Application arg0) {
		// do nothing
	}
}
