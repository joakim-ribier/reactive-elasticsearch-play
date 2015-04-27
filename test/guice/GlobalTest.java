package guice;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;

import play.Application;
import services.AuthenticationService;
import services.ESConstantImpl;
import services.ESConstantService;
import services.StubConfigurationImplTest;
import services.configuration.ConfigurationService;
import services.i18n.I18nImpl;
import services.i18n.I18nService;
import services.search.ESSearchImpl;
import services.search.ESSearchService;
import stub.StubAuthenticationImplTest;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;
import utils.file.FileUtils;
import utils.file.IFileUtils;

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
                bind(I18nService.class).to(I18nImpl.class);
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
        Client client = getClient();
        
        ConfigurationService configurationService = injector.getInstance(ConfigurationService.class);
        
        String indexName = configurationService.get(ESConstantImpl.ES_INDEX_NAME);
        
        // remove index
        client.admin().indices().prepareExists(indexName).execute(new ActionListener<IndicesExistsResponse>() {
            
            @Override
            public void onResponse(IndicesExistsResponse indicesExistsResponse) {
                if (indicesExistsResponse.isExists()) {
                    client.prepareDeleteByQuery(indexName)
                    .setQuery(QueryBuilders.matchAllQuery())
                    .execute().actionGet();
                    
                    client.admin().indices().refresh(new RefreshRequest(indexName)).actionGet();
                } else {
                    // create a new clean index
                    client.admin().indices().prepareCreate(indexName).execute().actionGet();
                }
            }
            
            @Override
            public void onFailure(Throwable arg0) {
                LOG.warn(arg0.getMessage(), arg0);
            }
        });
    }

    private Client getClient() {
        Client client = injector.getInstance(IESServerEmbedded.class).getClient();
        // waiting...
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            LOG.warn(e.getMessage(), e);
        }
        return client;
    }

    @Override
    public void onStop(Application arg0) {
        // do nothing
    }
    
    public boolean isNeedToStartESServerEmbedded() {
        return needToStartESServerEmbedded;
    }
}
