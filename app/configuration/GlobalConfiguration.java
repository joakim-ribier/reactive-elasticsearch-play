package configuration;

import play.Application;
import play.GlobalSettings;
import utils.eslasticsearch.ESServerEmbedded;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class GlobalConfiguration extends GlobalSettings {

    protected Injector injector;

    protected abstract AbstractModule buildAbstractModule();

    @Override
    public void onStart(Application application) {
        this.injector = Guice.createInjector(buildAbstractModule());
        // Initializes client of Elasticsearch
        injector.getInstance(ESServerEmbedded.class).getClient();
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
