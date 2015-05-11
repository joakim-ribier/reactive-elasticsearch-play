package configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Application;
import play.GlobalSettings;
import services.ESConstantImpl;
import services.ESConstantService;
import services.ZipImpl;
import services.ZipService;
import services.i18n.I18nImpl;
import services.i18n.I18nService;
import services.indexation.IndexationImpl;
import services.indexation.IndexationService;
import services.search.ESSearchImpl;
import services.search.ESSearchService;
import services.upload.UploadImpl;
import services.upload.UploadService;
import utils.EncodeUtils;
import utils.IEncodeUtils;
import utils.eslasticsearch.ESServerEmbedded;
import utils.eslasticsearch.IESServerEmbedded;
import utils.file.FileUtils;
import utils.file.IFileUtils;
import utils.xcontent.IXContentBuilderHelper;
import utils.xcontent.IXContentHelper;
import utils.xcontent.XContentBuilderHelper;
import utils.xcontent.XContentHelper;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class GlobalConfiguration extends GlobalSettings {
    
    protected static final Logger LOG = LoggerFactory
            .getLogger(GlobalConfiguration.class);
    
    protected Injector injector;
    
    protected abstract AbstractModule myModule();
    
    @Override
    public void onStart(Application application) {
        this.injector = Guice.createInjector(modules());
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
    
    protected AbstractModule defaultModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(IESServerEmbedded.class).to(ESServerEmbedded.class);
                bind(IEncodeUtils.class).to(EncodeUtils.class);
                bind(IFileUtils.class).to(FileUtils.class);
                bind(IXContentBuilderHelper.class).to(XContentBuilderHelper.class);
                bind(IXContentHelper.class).to(XContentHelper.class);
                
                bind(ESSearchService.class).to(ESSearchImpl.class);
                bind(ESConstantService.class).to(ESConstantImpl.class);
                bind(ZipService.class).to(ZipImpl.class);
                bind(I18nService.class).to(I18nImpl.class);
                bind(IndexationService.class).to(IndexationImpl.class);
                bind(UploadService.class).to(UploadImpl.class);
            }
        };
    }
    
    protected List<AbstractModule> modules() {
        return Lists.newArrayList(defaultModule(), myModule());
    }
    
}
