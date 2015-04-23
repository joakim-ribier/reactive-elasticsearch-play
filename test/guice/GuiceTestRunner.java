package guice;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import services.AuthenticationService;
import services.ESConstantImpl;
import services.ESConstantService;
import services.ESSearchImpl;
import services.ESSearchService;
import services.StubConfigurationImplTest;
import services.configuration.ConfigurationService;
import services.i18n.I18nImpl;
import services.i18n.I18nService;
import stub.StubAuthenticationImplTest;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;
import utils.file.FileUtils;
import utils.file.IFileUtils;

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
                    bind(IFileUtils.class).to(FileUtils.class);
                    
                    bind(ESSearchService.class).to(ESSearchImpl.class);
                    bind(ESConstantService.class).to(ESConstantImpl.class);
                    
                    bind(ConfigurationService.class).to(StubConfigurationImplTest.class);
                    bind(AuthenticationService.class).to(StubAuthenticationImplTest.class);
                    bind(I18nService.class).to(I18nImpl.class);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("An error occurred when initializing", e);
        }
    }

    @Override
    protected Object createTest() throws Exception {
        return injector.getInstance(this.getTestClass().getJavaClass());
    }
}
