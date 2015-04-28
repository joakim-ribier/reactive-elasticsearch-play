package guice;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.Client;

import play.Application;
import services.AuthenticationService;
import services.ESConstantImpl;
import services.StubConfigurationImplTest;
import services.configuration.ConfigurationService;
import stub.StubAuthenticationImplTest;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

import configuration.GlobalConfiguration;

public class GlobalTest extends GlobalConfiguration {
    
    private boolean needToStartESServerEmbedded;
    
    public GlobalTest() {
        this(false);
    }
    
    public GlobalTest(boolean needToStartESServerEmbedded) {
        this.needToStartESServerEmbedded = needToStartESServerEmbedded;
    }
    
    @Override
    protected AbstractModule myModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(ConfigurationService.class).to(StubConfigurationImplTest.class);
                bind(AuthenticationService.class).to(StubAuthenticationImplTest.class);
            }
        };
    }
    
    @Override
    public void onStart(Application application) {
        this.injector = Guice.createInjector(modules());
        if (isNeedToStartESServerEmbedded()) {
            Client client = getClient();
            try {
                Thread.sleep(1000);
                initESServerEmbeddedData(client);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    private Client getClient() {
        return injector.getInstance(IESServerEmbedded.class).getClient();
    }
    
    private void initESServerEmbeddedData(Client client) {
        ConfigurationService configurationService = injector.getInstance(ConfigurationService.class);
        String indexName = configurationService.get(ESConstantImpl.ES_INDEX_NAME);
        client.admin().indices().create(new CreateIndexRequest(indexName)).actionGet();
    }
    
    @Override
    public void onStop(Application arg0) {
        if (isNeedToStartESServerEmbedded()) {
            injector.getInstance(ESServerEmbedded.class).stop();
            Path path = Paths.get(".", "data");
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(path.toFile());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public boolean isNeedToStartESServerEmbedded() {
        return needToStartESServerEmbedded;
    }
    
}
