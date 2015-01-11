package guice;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;

import play.Application;
import services.AuthenticationService;
import services.ConfigurationService;
import services.ESConstantImpl;
import services.ESConstantService;
import services.ESSearchImpl;
import services.ESSearchService;
import services.StubConfigurationImplTest;
import stub.StubAuthenticationImplTest;
import utils.EncodeUtils;
import utils.FileUtils;
import utils.IEncodeUtils;
import utils.IFileUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

import configuration.GlobalConfiguration;
import controllers.AuthenticationController;

public class GlobalTest extends GlobalConfiguration {

    private boolean needToStartESServerEmbedded;

    public GlobalTest() {
        this(false);
    }
    
    public GlobalTest(boolean needToStartESServerEmbedded) {
        this.needToStartESServerEmbedded = needToStartESServerEmbedded;
    }
    
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

                bind(ConfigurationService.class).to(StubConfigurationImplTest.class);
                bind(AuthenticationService.class).to(StubAuthenticationImplTest.class);

                bind(AuthenticationController.class).asEagerSingleton();
            }
        };
    }

    @Override
    public void onStart(Application application) {
        this.injector = Guice.createInjector(buildAbstractModule());
        if (isNeedToStartESServerEmbedded()) {
            injector.getInstance(ESServerEmbedded.class).getClient();
            initESServerEmbeddedData();
        }
    }

    private void initESServerEmbeddedData() {
        Client client = injector.getInstance(IESServerEmbedded.class).getClient();
        ConfigurationService configurationService = injector.getInstance(ConfigurationService.class);
        
        String indexName = configurationService.get(ESConstantImpl.ES_INDEX_NAME);
        
        // remove index
        IndicesExistsResponse indicesExistsResponse = client.admin().indices().exists(
                new IndicesExistsRequest(indexName)).actionGet();
        
        if (indicesExistsResponse.isExists()) {
            client.admin().indices().prepareDelete(indexName).execute().actionGet();
        }
        
        // create a new clean index
        client.admin().indices().prepareCreate(indexName).execute().actionGet();
    }

    @Override
    public void onStop(Application arg0) {
        // do nothing
    }
    
    public boolean isNeedToStartESServerEmbedded() {
        return needToStartESServerEmbedded;
    }
}
