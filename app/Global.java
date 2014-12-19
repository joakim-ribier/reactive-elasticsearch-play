import services.AuthenticationImpl;
import services.AuthenticationService;
import services.ConfigurationImpl;
import services.ConfigurationService;
import services.ESConstantImpl;
import services.ESConstantService;
import services.ESSearchImpl;
import services.ESSearchService;
import utils.EncodeUtils;
import utils.FileUtils;
import utils.IEncodeUtils;
import utils.IFileUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;

import com.google.inject.AbstractModule;

import configuration.GlobalConfiguration;

public class Global extends GlobalConfiguration {

    @Override
    protected AbstractModule buildAbstractModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(IESServerEmbedded.class).to(ESServerEmbedded.class);
                bind(IEncodeUtils.class).to(EncodeUtils.class);
                bind(IFileUtils.class).to(FileUtils.class);
                
                bind(ESSearchService.class).to(ESSearchImpl.class);
                bind(ESConstantService.class).to(ESConstantImpl.class);
                bind(ConfigurationService.class).to(ConfigurationImpl.class);
                bind(AuthenticationService.class).to(AuthenticationImpl.class);
            }
        };
    }
}
