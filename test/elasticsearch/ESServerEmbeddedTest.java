package elasticsearch;

import guice.GuiceTestRunner;

import org.junit.runner.RunWith;

import services.ESConstantService;
import utils.eslasticsearch.IESServerEmbedded;

import com.google.inject.Inject;

@RunWith(GuiceTestRunner.class)
public abstract class ESServerEmbeddedTest {

    @Inject
    protected IESServerEmbedded iesServerEmbedded;
    @Inject
    protected ESConstantService elasticsearchConstantService;

}
