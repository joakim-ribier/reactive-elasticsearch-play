package guice;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import services.AuthenticationImpl;
import services.AuthenticationService;
import services.ConfigurationImplTest;
import services.ConfigurationService;
import services.ESConstantImplTest;
import services.ESConstantService;
import services.ESSearchImpl;
import services.ESSearchService;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class GuiceTestRunner extends BlockJUnit4ClassRunner {

    private final Injector injector;

    public GuiceTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        try {
            this.injector = Guice.createInjector(new AbstractModule() {
                @Override
                protected void configure() {
                    bind(IESServerEmbedded.class).to(ESServerEmbedded.class);
                    bind(IEncodeUtils.class).to(EncodeUtils.class);
                    
                    bind(ESSearchService.class).to(ESSearchImpl.class);
                    bind(ESConstantService.class).to(ESConstantImplTest.class);
                    bind(ConfigurationService.class).to(ConfigurationImplTest.class);
                    bind(AuthenticationService.class).to(AuthenticationImpl.class);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("An error occurred when initializing", e);
        }
    }

    @Override
    protected Object createTest() throws Exception {
        return this.injector.getInstance(this.getTestClass().getJavaClass());
    }
}
